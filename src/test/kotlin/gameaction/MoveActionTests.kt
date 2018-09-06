package gameaction

import createTestGame
import engine.Direction
import engine.getPlayerLocation
import gameactions.MoveAction
import models.Card
import org.junit.Assert.*
import org.junit.Test

class MoveActionTests {

    @Test
    fun test_cards_to_discard() {
        val game = createTestGame()

        val action = MoveAction(game.players[0], Card(3), Direction.LEFT)

        val discard = action.cardsToDiscard

        assertEquals(discard.size, 1)
        assertEquals(discard[0], Card(3))
    }

    @Test
    fun test_pinned_check_works() {
        val game = createTestGame()

        // Test pinned left
        game.board.movePlayer(1, -16)
        var moveAction = MoveAction(game.players[0], Card(1), Direction.LEFT)
        assertFalse(moveAction.canTake(game))
        moveAction = MoveAction(game.players[0], Card(1), Direction.RIGHT)
        assertFalse(moveAction.canTake(game))

        // Test pinned right
        game.board.movePlayer(0, 17)
        game.board.movePlayer(1, 15)
        moveAction = MoveAction(game.players[0], Card(1), Direction.LEFT)
        assertFalse(moveAction.canTake(game))
        moveAction = MoveAction(game.players[0], Card(1), Direction.RIGHT)
        assertFalse(moveAction.canTake(game))
    }

    @Test
    fun test_when_adjacent_to_opponent_cannot_move() {
        val game = createTestGame()

        game.board.movePlayer(0, 8)
        game.board.movePlayer(1, -8)

        // Test can't move when they are on the right of you and you try to move right
        var moveAction = MoveAction(game.players[0], Card(2), Direction.RIGHT)
        assertFalse(moveAction.canTake(game))

        game.board.movePlayer(1, -2)

        moveAction = MoveAction(game.players[0], Card(2), Direction.LEFT)
        assertFalse(moveAction.canTake(game))
    }

    @Test
    fun test_can_move_if_wide_open() {
        val game = createTestGame()

        val moveAction = MoveAction(game.players[0], Card(3), Direction.RIGHT)
        assertTrue(moveAction.canTake(game))
    }

    @Test
    fun test_cant_move_past_opponent() {
        // Test you cant move through an opponent on the right
        var game = createTestGame()

        game.board.movePlayer(0, 7)
        game.board.movePlayer(1, -7)

        var moveAction = MoveAction(game.players.first(), Card(5), Direction.RIGHT)
        assertTrue(moveAction.canTake(game))

        moveAction.takeAction(game)
        assertEquals(game.getPlayerLocation(game.players.first()), 9)

        // Test you cant move through an opponent on the left
        game = createTestGame()

        game.board.movePlayer(0, 7)
        game.board.movePlayer(1, -7)

        moveAction = MoveAction(game.players[1], Card(5), Direction.LEFT)
        assertTrue(moveAction.canTake(game))

        moveAction.takeAction(game)
        assertEquals(game.getPlayerLocation(game.players[1]), 8)
    }
}