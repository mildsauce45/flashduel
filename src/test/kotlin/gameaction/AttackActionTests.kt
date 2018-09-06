package gameaction

import createTestGame
import gameactions.AttackAction
import models.Card
import models.Game
import org.junit.Assert.*
import org.junit.Test

class AttackActionTests {

    @Test
    fun test_cards_to_discard() {
        val game = createTestGame()

        val action = AttackAction(game.players[0], listOf(Card(2), Card(2)))

        val discard = action.cardsToDiscard

        assertEquals(discard.size, 2)
        assertEquals(discard.count { it.value == 2 }, 2)
    }

    @Test
    fun test_can_attack_at_proper_distance() {
        val game = createTestGame()

        setupGameForAction(game)

        val hand = listOf(Card(3), Card(3))
        var action = AttackAction(game.players[0], hand)

        assertTrue(action.canTake(game))

        action = AttackAction(game.players[1], hand)

        assertTrue(action.canTake(game))
    }

    @Test
    fun test_cannot_attack_outside_distance() {
        val game = createTestGame()

        setupGameForAction(game)

        val hand = listOf(Card(2))

        var action = AttackAction(game.players[0], hand)

        assertFalse(action.canTake(game))

        action = AttackAction(game.players[1], hand)

        assertFalse(action.canTake(game))
    }

    @Test
    fun test_unblocked_attack_kills() {
        val game = createTestGame()

        setupGameForAction(game)

        val action = AttackAction(game.players[0], listOf(Card(3)))

        assertTrue(action.canTake(game))

        action.takeAction(game)

        assertTrue(game.isGameOver)
    }

    private fun setupGameForAction(game: Game) {
        game.board.movePlayer(0, 7)
        game.board.movePlayer(1, -7)
    }
}
