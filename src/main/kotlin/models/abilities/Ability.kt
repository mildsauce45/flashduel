package models.abilities

import gameactions.AttackAction
import gameactions.DashAttackAction
import models.GameState

abstract class Ability(val name: String, private val triggers: List<Trigger>, private val gameState: GameState) {
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

enum class Trigger {
    START_TURN,
    ATTACK,
    DASH_ATTACK,
    CUSTOM
}

fun Trigger.isTriggering(ability: Ability, context: AbilityContext): Boolean {
    return when (this) {
        Trigger.CUSTOM -> ability.shouldTriggerCustom(context)
        Trigger.ATTACK -> context.action != null && context.action is AttackAction
        Trigger.DASH_ATTACK -> context.action != null && context.action is DashAttackAction
        Trigger.START_TURN -> context.game.currentState == GameState.START_TURN
    }
}