package gameaction.reactions

import createTestGame
import engine.getPlayerLocation
import gameactions.reactions.RetreatReaction
import models.Card
import org.junit.Assert.*
import org.junit.Test

class RetreatReactionTests {
    @Test
    fun test_canTake_fails_at_edges() {
        val game = createTestGame()

        val p1Retreat = RetreatReaction(game.players[0], Card(3))

        assertFalse(p1Retreat.canTake(game))

        val p2Retreat = RetreatReaction(game.players[1], Card(3))

        assertFalse(p2Retreat.canTake(game))
    }

    @Test
    fun test_canTake_succeeds() {
        val game = createTestGame()

        game.board.movePlayer(0, 2)
        game.board.movePlayer(1, -2)

        val p1Retreat = RetreatReaction(game.players[0], Card(3))

        assertTrue(p1Retreat.canTake(game))

        val p2Retreat = RetreatReaction(game.players[1], Card(3))

        assertTrue(p2Retreat.canTake(game))
    }

    @Test
    fun test_take_moves_and_flags() {
        val game = createTestGame()

        game.board.movePlayer(0, 2)
        game.board.movePlayer(1, -2)

        val p1Retreat = RetreatReaction(game.players[0], Card(3))
        val p2Retreat = RetreatReaction(game.players[1], Card(3))

        p1Retreat.take(game)

        assertEquals(0, game.getPlayerLocation(game.players[0]))
        assertTrue(game.players[0].isRecovering)

        p2Retreat.take(game)

        assertEquals(17, game.getPlayerLocation(game.players[1]))
        assertTrue(game.players[1].isRecovering)
    }
}