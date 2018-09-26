package models.abilities.rook

import gameactions.AttackAction
import models.GameState
import models.abilities.Ability
import models.abilities.AbilityContext
import models.abilities.Trigger

class WindmillCrusher: Ability("Windmill Crusher", listOf(Trigger.ATTACK, Trigger.CUSTOM), GameState.GET_ACTION) {
    override fun shouldTriggerCustom(context: AbilityContext): Boolean {
        if (context.action == null || context.action !is AttackAction)
            return false

        return context.action.attackCards[0].value == 1
    }

    override fun apply(context: AbilityContext) {
        (context.action as AttackAction).isBlockable = false
    }
}
