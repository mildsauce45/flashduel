package models.abilities.jaina

import models.Card
import models.GameState
import models.abilities.Ability
import models.abilities.AbilityContext
import models.abilities.Trigger

class SmolderingEmbers : Ability(
        "Smoldering Embers",
        "You may retrieve a 5 from the discard pile",
        listOf(Trigger.START_TURN, Trigger.CUSTOM),
        GameState.START_TURN) {
    companion object {
        fun cardIs5(c: Card): Boolean {
            return c.value == 5
        }
    }

    override fun shouldTriggerCustom(context: AbilityContext): Boolean {
        return context.game.discardPile.any(::cardIs5)
    }

    override fun apply(context: AbilityContext) {
        val card = context.game.discardPile.first(::cardIs5)
        context.game.discardPile.remove(card)
        context.game.currentPlayer.draw(card)
    }
}