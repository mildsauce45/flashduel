package engine.strategies

import gameactions.GameAction
import models.Game

interface PlayerStrategy {
    fun startTurn(game: Game)
    fun getNextAction(game: Game): GameAction
}