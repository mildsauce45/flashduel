package models.abilities.jaina

import createTestGame
import models.Card
import models.GameState
import models.abilities.AbilityContext
import models.abilities.jaina.SmolderingEmbers.Companion.cardIs5
import org.junit.Assert.*
import org.junit.Test

class SmolderingEmbersTests {
    private val ability = SmolderingEmbers()

    @Test
    fun test_can_use() {
        val game = createTestGame()
        game.stateTransition(GameState.START_TURN)

        val context = AbilityContext(game, null, null)

        assertFalse("Discard pile does not contain a 5", ability.canUse(context))

        game.discardPile.add(Card(5))

        assertTrue("Discard pile contains a 5", ability.canUse(context))
    }

    @Test
    fun test_use() {
        val game = createTestGame()
        game.stateTransition(GameState.START_TURN)
        game.discardPile.add(Card(5))
        game.start()

        val previousCount = game.currentPlayer.hand.count(::cardIs5)

        val context = AbilityContext(game, null, null)

        ability.use(context)

        assertEquals("Discard pile should be empty", game.discardPile.size, 0)
        assertEquals("Should have drawn the 5 off of the pile", game.currentPlayer.hand.count(::cardIs5), previousCount + 1)
    }
}