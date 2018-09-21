package engine

import gameactions.RequiresReaction
import gameactions.reactions.TakeHitReaction
import models.Game
import models.GameState
import models.Player
import views.GameView
import kotlin.math.abs

class GamePlayer(private val game: Game, private val view: GameView) {
    private var _currentTarget: Player? = null
    private var _currentAction: RequiresReaction? = null

    fun play() {
        while (!game.isGameOver) {
            val nextState = when (game.currentState) {
                GameState.START_GAME -> startGame()
                GameState.START_TURN -> startTurn()
                GameState.GET_ACTION -> getAction()
                GameState.GET_RESPONSE -> getResponse()
                GameState.END_TURN -> endTurn()
                GameState.TIME_OVER -> doTimeOver()
            }

            game.stateTransition(nextState)
        }

        // TODO make this handle more than 2 players
        when {
            game.isDraw -> view.showMessage("Game ends in a draw")
            game.players[0].isAlive -> view.showMessage("${game.players[0].name} wins!")
            game.players[1].isAlive -> view.showMessage("${game.players[1].name} wins!")
        }
    }

    private fun startGame(): GameState {
        game.start()
        return GameState.START_TURN
    }

    private fun startTurn(): GameState {
        view.display()

        val isPlayerRecovering = game.currentPlayer.isRecovering

        game.currentPlayer.strategy.startTurn(game)

        return when {
            isPlayerRecovering -> GameState.END_TURN
            else -> GameState.GET_ACTION
        }
    }

    private fun getAction(): GameState {
        lateinit var nextState: GameState

        // Some strategies cause cards to be drawn at the start of the turn, so check for TimeOver
        if (game.isTimeOver) {
            nextState = GameState.TIME_OVER
        } else {

            // Get the current players action
            val action = game.currentPlayer.strategy.getNextAction(game)
            _currentTarget = action.takeAction(game)

            view.showMessage(action.asMessage)

            nextState = when {
                _currentTarget != null && action is RequiresReaction -> GameState.GET_RESPONSE
                else -> GameState.END_TURN
            }

            if (action is RequiresReaction)
                _currentAction = action
        }

        return nextState
    }

    private fun getResponse(): GameState {
        // Previous state should verify these values are valid, otherwise we should'nt be here
        val reaction = _currentTarget!!.strategy.getReaction(_currentAction!!, game)

        // Some strategies require cards to be drawn while getting a reaction, so check for TimeOver
        return if (game.isTimeOver) {
            GameState.TIME_OVER
        } else {
            reaction.take(game)
            view.showMessage(reaction.asMessage)

            GameState.END_TURN
        }
    }

    private fun endTurn(): GameState {
        game.currentPlayer.drawToFive(game)

        return if (game.isTimeOver)
            GameState.TIME_OVER
        else {
            game.switchPlayer()
            GameState.START_TURN
        }
    }

    private fun doTimeOver(): GameState {
        view.showMessage("Game ending due to time")

        // TODO: make it work for games with 4 players
        val distance = abs(game.getPlayerLocation(game.players[0]) - game.getPlayerLocation(game.players[1]))

        val p1Cards = game.players[0].hand.filter { it.value == distance }
        val p2Cards = game.players[1].hand.filter { it.value == distance }

        view.showMessage("${game.players[0].name} can attack with ${p1Cards.size} cards")
        view.showMessage("${game.players[1].name} can attack with ${p2Cards.size} cards")

        val cardsDiff = p1Cards.size - p2Cards.size
        when {
            cardsDiff == 0 -> checkAdvancementAmount()
            cardsDiff < 0 -> TakeHitReaction(game.players[0]).take(game)
            else -> TakeHitReaction(game.players[1]).take(game)
        }

        return GameState.START_TURN
    }

    private fun checkAdvancementAmount() {
        val p1Location = game.getPlayerLocation(game.players[0])
        val p2Location = game.getPlayerLocation(game.players[1])

        val advDiff = p1Location - (17 - p2Location)
        when {
            advDiff == 0 -> game.isDraw = true
            advDiff < 0 -> { view.showMessage("${game.players[1].name} advanced more"); TakeHitReaction(game.players[0]).take(game) }
            else -> { view.showMessage("${game.players[0].name} advanced more"); TakeHitReaction(game.players[1]).take(game) }
        }
    }
}
