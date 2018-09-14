package engine

import gameactions.reactions.TakeHitReaction
import models.Game
import kotlin.math.abs

class GamePlayer(private val game: Game) {
    fun play() {
        game.start()

        while (!game.isGameOver) {
            val isPlayerRecovering = game.currentPlayer.isRecovering

            game.currentPlayer.strategy.startTurn(game)

            if (!isPlayerRecovering) {
                // Some strategies cause cards to be drawn at the start of the turn, so check for TimeOver
                if (game.isTimeOver) {
                    doTimeOver()
                    continue
                }

                // Get the current players action
                val action = game.currentPlayer.strategy.getNextAction(game)
                val target = action.takeAction(game)

                // If the action requires a response from an opponent
                if (target != null) {
                    val reaction = target.strategy.getReaction(action, game)

                    // Some strategies require cards to be drawn while getting a reaction, so check for TimeOver
                    if (game.isTimeOver) {
                        doTimeOver()
                        continue
                    }

                    reaction.take(game)
                }
            }

            game.currentPlayer.drawToFive(game)

            if (game.isTimeOver)
                doTimeOver()

            game.switchPlayer()
        }
    }

    private fun doTimeOver() {
        // TODO: make it work for games with 4 players
        val distance = abs(game.getPlayerLocation(game.players[0]) - game.getPlayerLocation(game.players[1]))

        val p1Cards = game.players[0].hand.filter { it.value == distance }
        val p2Cards = game.players[1].hand.filter { it.value == distance }

        val cardsDiff = p1Cards.size - p2Cards.size
        when {
            cardsDiff == 0 -> checkAdvancementAmount()
            cardsDiff < 0 -> TakeHitReaction(game.players[0]).take(game)
            else -> TakeHitReaction(game.players[1]).take(game)
        }
    }

    private fun checkAdvancementAmount() {
        val p1Location = game.getPlayerLocation(game.players[0])
        val p2Location = game.getPlayerLocation(game.players[1])

        val advDiff = p1Location - (17 - p2Location)
        when {
            advDiff == 0 -> game.isDraw = true
            advDiff < 0 -> TakeHitReaction(game.players[0]).take(game)
            else -> TakeHitReaction(game.players[1]).take(game)
        }
    }
}