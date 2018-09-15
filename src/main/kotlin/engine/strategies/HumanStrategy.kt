package engine.strategies

import gameactions.GameAction
import gameactions.RequiresReaction
import gameactions.reactions.Reaction
import models.Game
import models.Player

class HumanStrategy: PlayerStrategy {

    override lateinit var player: Player

    override fun startTurn(game: Game) {
        // Humans currently don't get to do anything fun on the start of their turn
        // When abilities are added, they'll get refreshed here
        player.isRecovering = false
    }

    override fun getNextAction(game: Game): GameAction {
        throw NotImplementedError()
    }

    override fun getReaction(action: RequiresReaction, game: Game): Reaction {
        throw NotImplementedError()
    }
}
