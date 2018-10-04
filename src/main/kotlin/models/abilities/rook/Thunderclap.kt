package models.abilities.rook

import gameactions.DashAttackAction
import models.GameState
import models.abilities.Ability
import models.abilities.AbilityContext
import models.abilities.Trigger

class Thunderclap : Ability(
        "Thunderclap",
        "When you dash attack with 1s as the attack, the opponent cannot retreat",
        listOf(Trigger.DASH_ATTACK, Trigger.CUSTOM),
        GameState.GET_ACTION) {
    override fun shouldTriggerCustom(context: AbilityContext): Boolean {
        if (context.action == null || context.action !is DashAttackAction)
            return false

        return context.action.attackCards[0].value == 1
    }

    override fun apply(context: AbilityContext) {
        (context.action as DashAttackAction).canRetreat = false
    }
}