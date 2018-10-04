package models.abilities

import models.GameState

abstract class Ability(val name: String, val description: String, private val triggers: List<Trigger>, private val gameState: GameState) {
    var usedThisTurn: Boolean = false

    open fun shouldTriggerCustom(context: AbilityContext): Boolean {
        return true
    }

    protected abstract fun apply(context: AbilityContext)

    fun canUse(context: AbilityContext): Boolean {
        if (usedThisTurn || context.game.currentState != gameState)
            return false

        var canUse = true
        for (t in triggers)
            canUse = canUse && t.isTriggering(this, context)

        return canUse
    }

    fun use(context: AbilityContext) {
        apply(context)

        usedThisTurn = true
    }
}