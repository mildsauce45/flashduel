package models.abilities.rook

import createTestGame
import gameactions.AttackAction
import gameactions.DashAttackAction
import models.Card
import models.GameState
import models.abilities.AbilityContext
import org.junit.Assert.*
import org.junit.Test

class WindmillCrusherTests {
    private val ability = WindmillCrusher()
    private val game = createTestGame()

    init {
        game.stateTransition(GameState.GET_ACTION)
    }

    @Test
    fun test_can_use_when_attacking_with_1s() {
        val action = AttackAction(game.players[0], listOf(Card(1), Card(1)))

        val context = AbilityContext(game, action)

        assertTrue(ability.canUse(context))
    }

    @Test
    fun test_cannot_use_if_dash_attack() {
        val action = DashAttackAction(game.players[0], Card(2), listOf(Card(1)))

        val context = AbilityContext(game, action)

        assertFalse(ability.canUse(context))
    }

    @Test
    fun test_cannot_use_if_not_attacking_with_1s() {
        val action = AttackAction(game.players[0], listOf(Card(2), Card(2)))

        val context = AbilityContext(game, action)

        assertFalse(ability.canUse(context))
    }

    @Test
    fun test_cannot_use_if_theres_no_attack() {
        val context = AbilityContext(game)

        assertFalse(ability.canUse(context))
    }

    @Test
    fun test_makes_attack_unblockable() {
        val action = AttackAction(game.players[0], listOf(Card(1), Card(1)))
        val context = AbilityContext(game, action)

        ability.use(context)

        assertFalse(action.isBlockable)
    }
}