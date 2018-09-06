package gameaction

import createTestGame
import engine.getPlayerLocation
import gameactions.PushAction
import models.Card
import org.junit.Assert.*
import org.junit.Test

class PushActionTests {

    @Test
    fun test_cards_to_discard() {
        val game = createTestGame()

        val action = PushAction(game.players[0], Card(4))

        val discard = action.cardsToDiscard

        assertEquals(discard.size, 1)
        assertEquals(discard[0], Card(4))
    }

    @Test
    fun test_can_push_when_adjacent() {
        val game = createTestGame()

        game.board.movePlayer(0, 16)

        val p1Action = PushAction(game.players[0], Card(4))

        assertTrue(p1Action.canTake(game))

        val p2Action = PushAction(game.players[1], Card(1))

        assertTrue(p2Action.canTake(game))
    }

    @Test
    fun test_cannot_push_when_not_adjacent() {
        val game = createTestGame()

        game.board.movePlayer(0, 15)

        val p1Action = PushAction(game.players[0], Card(4))

        assertFalse(p1Action.canTake(game))

        val p2Action = PushAction(game.players[1], Card(1))

        assertFalse(p2Action.canTake(game))
    }

    @Test
    fun test_push_works_in_the_open() {
        val game = createTestGame()

        game.board.movePlayer(0, 8)
        game.board.movePlayer(1, -8)

        val p1Action = PushAction(game.players[0], Card(2))

        assertTrue(p1Action.canTake(game))

        p1Action.takeAction(game)

        assertEquals(game.getPlayerLocation(game.players[1]), 11)

        game.board.movePlayer(1, -2)

        val p2Action = PushAction(game.players[1], Card(3))

        assertTrue(p2Action.canTake(game))

        p2Action.takeAction(game)

        assertEquals(game.getPlayerLocation(game.players[0]), 5)
    }

    @Test
    fun test_push_clamps_to_edges() {
        val game = createTestGame()

        game.board.movePlayer(0, 15)
        game.board.movePlayer(1, -1)

        val p1Action = PushAction(game.players[0], Card(Card.MAX_VALUE))

        assertTrue(p1Action.canTake(game))

        p1Action.takeAction(game)

        assertEquals(game.getPlayerLocation(game.players[1]), 17)

        game.board.movePlayer(0, -14)
        game.board.movePlayer(1, -15)

        val p2Action = PushAction(game.players[1], Card(Card.MAX_VALUE))

        assertTrue(p2Action.canTake(game))

        p2Action.takeAction(game)

        assertEquals(game.getPlayerLocation(game.players[0]), 0)
    }
}