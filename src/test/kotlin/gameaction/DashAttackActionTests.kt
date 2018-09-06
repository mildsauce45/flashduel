package gameaction

import createTestGame
import engine.getPlayerLocation
import gameactions.DashAttackAction
import models.Card
import models.Game
import org.junit.Assert.*
import org.junit.Test

class DashAttackActionTests {

    @Test
    fun test_cards_to_discard() {
        val game = createTestGame()

        val action = DashAttackAction(game.players[0], Card(Card.MAX_VALUE), listOf(Card(3), Card(3)))

        val discard = action.cardsToDiscard

        assertEquals(discard.size, 3)
        assertEquals(discard.count { it.value == Card.MAX_VALUE }, 1)
        assertEquals(discard.count { it.value == 3 }, 2)
    }

    @Test
    fun test_can_dash_attack_with_1s() {
        val game = setupGame()

        val p1Action = DashAttackAction(game.players[0], Card(Card.MAX_VALUE), listOf(Card(1)))

        assertTrue(p1Action.canTake(game))

        val p2Action = DashAttackAction(game.players[0], Card(Card.MAX_VALUE), listOf(Card(1)))

        assertTrue(p2Action.canTake(game))
    }

    @Test
    fun test_can_dash_if_cards_add_up_to_proper_value() {
        val game = setupGame()

        val p1Action = DashAttackAction(game.players[0], Card(3), listOf(Card(2), Card(2)))

        assertTrue(p1Action.canTake(game))

        val p2Action = DashAttackAction(game.players[1], Card(3), listOf(Card(2)))

        assertTrue(p2Action.canTake(game))
    }

    @Test
    fun test_cannot_dash_if_cards_dont_add_up() {
        val game = setupGame()

        val p1Action = DashAttackAction(game.players[0], Card(1), listOf(Card(2), Card(2)))

        assertFalse(p1Action.canTake(game))

        val p2Action = DashAttackAction(game.players[1], Card(4), listOf(Card(Card.MAX_VALUE)))

        assertFalse(p2Action.canTake(game))
    }

    @Test
    fun test_unanswered_dash_kills() {
        val game = setupGame()

        val p1Action = DashAttackAction(game.players[0], Card(3), listOf(Card(2), Card(2)))

        assertTrue(p1Action.canTake(game))

        p1Action.takeAction(game)

        assertEquals(game.getPlayerLocation(game.players[0]), 8)
        assertTrue(game.isGameOver)
        assertFalse(game.players[1].isAlive)
    }

    @Test
    fun test_unanswered_dash_kills_1s() {
        val game = setupGame()

        val p1Action = DashAttackAction(game.players[0], Card(Card.MAX_VALUE), listOf(Card(1)))

        assertTrue(p1Action.canTake(game))

        p1Action.takeAction(game)

        assertEquals(game.getPlayerLocation(game.players[0]), 9)
        assertTrue(game.isGameOver)
        assertFalse(game.players[1].isAlive)
    }

    private fun setupGame(): Game {
        val game = createTestGame()

        game.board.movePlayer(0, 5)
        game.board.movePlayer(1, -7)

        return game
    }
}