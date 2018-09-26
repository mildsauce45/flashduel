package engine.strategies

import gameactions.GameAction
import gameactions.RequiresReaction
import gameactions.reactions.Reaction
import models.Game
import models.abilities.AbilityContext

/**
 * Rook follows the same patterns as the training dummy, but has access to his abilities
 * and uses them whenever possible
 */
class RookStrategy: TrainingDummyStrategy() {
    override fun getNextAction(game: Game): GameAction {
        val gameAction = super.getNextAction(game)

        val context = AbilityContext(game, gameAction)

        player.abilities.firstOrNull { it.canUse(context) }?.use(context)

        return gameAction
    }

    override fun getReaction(action: RequiresReaction, game: Game): Reaction {
        return super.getReaction(action, game)
    }
}