package engine.strategies

import gameactions.GameAction
import gameactions.reactions.Reaction
import models.Game
import models.Player

interface PlayerStrategy {
    var player: Player

    fun startTurn(game: Game)
    fun getNextAction(game: Game): GameAction
    fun getReaction(action: GameAction, game: Game): Reaction?
}