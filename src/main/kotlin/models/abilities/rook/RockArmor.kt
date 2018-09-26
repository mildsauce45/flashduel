package models.abilities.rook

import models.GameState
import models.abilities.Ability
import models.abilities.AbilityContext
import models.abilities.Trigger

class RockArmor: Ability("Rock Armor", listOf(Trigger.DASH_ATTACK), GameState.GET_RESPONSE) {
    override fun apply(context: AbilityContext) {
        // Allows you to block with cards higher than the value of the attack
        // Includes pairs
    }
}