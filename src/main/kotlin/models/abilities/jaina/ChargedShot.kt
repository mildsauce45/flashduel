package models.abilities.jaina

import models.GameState
import models.abilities.Ability
import models.abilities.AbilityContext
import models.abilities.Trigger

class ChargedShot : Ability(
        "Charged Shot",
        "Reveal a card from your hand. Push an opponent that many spaces",
        listOf(Trigger.START_TURN),
        GameState.START_TURN) {
    override fun apply(context: AbilityContext) {

    }
}