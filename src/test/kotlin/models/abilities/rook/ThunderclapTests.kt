package models.abilities.rook

import createTestGame
import gameactions.AttackAction
import gameactions.DashAttackAction
import models.Card
import models.GameState
import models.abilities.AbilityContext
import org.junit.Assert.*
import org.junit.Test

class ThunderclapTests {
    private val ability = Thunderclap()
    private val game = createTestGame()

    init {
        game.stateTransition(GameState.GET_ACTION)
    }

    @Test
    fun test_can_use_when_dash_attacking_with_1s() {
        val action = DashAttackAction(game.players[0], Card(2), listOf(Card(1)))

        val context = AbilityContext(game, action)

        assertTrue(ability.canUse(context))
    }

    @Test
    fun test_cannot_use_if_attacking() {
        val action = AttackAction(game.players[0], listOf(Card(1), Card(1)))

        val context = AbilityContext(game, action)

        assertFalse(ability.canUse(context))
    }

    @Test
    fun test_cannot_use_if_not_dash_attacking_with_1s() {
        val action = DashAttackAction(game.players[0], Card(3), listOf(Card(2), Card(2)))

        val context = AbilityContext(game, action)

        assertFalse(ability.canUse(context))
    }

    @Test
    fun test_cannot_use_if_theres_no_attack() {
        assertFalse(ability.canUse(AbilityContext(game)))
    }

    @Test
    fun test_prevents_retreat() {
        val action = DashAttackAction(game.players[0], Card(2), listOf(Card(1)))
        val context = AbilityContext(game, action)

        ability.use(context)

        assertFalse(action.canRetreat)
    }
}