package gameaction

import createTestGame
import gameactions.AttackAction
import models.Card
import models.Game
import org.junit.Assert.*
import org.junit.Test

class AttackActionTests {
    @Test
    fun test_can_attack_at_proper_distance() {
        val game = createTestGame()

        setupGameForAction(game)

        val hand = listOf(Card(3), Card(3))
        var action = AttackAction(game.players.first(), hand)

        assertTrue(action.canTake(game))

        action = AttackAction(game.players[1], hand)

        assertTrue(action.canTake(game))
    }

    @Test
    fun test_cannot_attack_outside_distance() {
        val game = createTestGame()

        setupGameForAction(game)

        val hand = listOf(Card(2))

        var action = AttackAction(game.players.first(), hand)

        assertFalse(action.canTake(game))

        action = AttackAction(game.players[1], hand)

        assertFalse(action.canTake(game))
    }

    @Test
    fun test_unblocked_attack_kills() {
        val game = createTestGame()

        setupGameForAction(game)

        val action = AttackAction(game.players.first(), listOf(Card(3)))

        assertTrue(action.canTake(game))

        action.takeAction(game)

        assertTrue(game.isGameOver)
    }

    private fun setupGameForAction(game: Game) {
        game.board.movePlayer(0, 7)
        game.board.movePlayer(1, -7)
    }
}
