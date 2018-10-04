package models.abilities

import gameactions.AttackAction
import gameactions.DashAttackAction
import models.GameState

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