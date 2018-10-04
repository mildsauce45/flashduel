package engine.strategies

import gameactions.GameAction
import gameactions.RequiresReaction
import gameactions.reactions.Reaction
import models.Game
import models.abilities.Ability
import models.abilities.AbilityContext

/**
 * Rook follows the same patterns as the training dummy, but has access to his abilities
 * and uses them whenever possible
 */
class RookStrategy: TrainingDummyStrategy() {
    override fun useAbility(ability: Ability): Boolean {
        return true
    }
}