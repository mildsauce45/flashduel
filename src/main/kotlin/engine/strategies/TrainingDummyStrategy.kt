package engine.strategies

import engine.Direction
import engine.getOpponentLocations
import engine.getPlayerLocation
import gameactions.*
import models.Card
import models.Game
import kotlin.math.abs

// ** Bots turn **
// 1) Attacks if it can, always powering up with pairs, triples, etc
// 2) If adjacent to enemy, he pushes with the card it drew for the turn
// 3) If it can dash attack, using the card it drew for the turn as the dash, it does (powering up as much as possible)
// 4) Moves forward with the card it drew this turn
// ** Your turn **
// 1) When attacked or dash attacked, draws an extra card, then blocks, if possible
// 2) If it cannot block it retreats with the extra card it just drew
class TrainingDummyStrategy: PlayerStrategy {
    private lateinit var _thisTurnsCard: Card

    override fun startTurn(game: Game) {
        _thisTurnsCard = game.deck.draw()

        game.currentPlayer.draw(_thisTurnsCard)
    }

    override fun getNextAction(game: Game): GameAction {
        if (isAdjacentToOpponent(game))
            return PushAction(game.currentPlayer, _thisTurnsCard)

        return MoveAction(game.currentPlayer, _thisTurnsCard, Direction.LEFT)
    }

    private fun isAdjacentToOpponent(game: Game): Boolean {
        val playerLocation = game.getPlayerLocation(game.currentPlayer)
        val opponentLocations = game.getOpponentLocations(game.currentPlayer)

        return opponentLocations.any { abs(it - playerLocation) == 1 }
    }
}