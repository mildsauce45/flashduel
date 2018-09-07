package engine.strategies

import engine.Direction
import gameactions.GameAction
import gameactions.MoveAction
import gameactions.PushAction
import models.Card
import models.Game

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
        return MoveAction(game.currentPlayer, _thisTurnsCard, Direction.LEFT)
    }
}