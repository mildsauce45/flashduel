package engine.strategies

import gameactions.GameAction
import models.Game

class HumanStrategy: PlayerStrategy {
    override fun startTurn(game: Game) {
        // Humans currently don't get to do anything fun on the start of their turn
        // When abilities are added, they'll get refreshed here
    }

    override fun getNextAction(game: Game): GameAction {
        throw NotImplementedError()
    }
}