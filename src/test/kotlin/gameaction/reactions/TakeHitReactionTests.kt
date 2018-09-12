package gameaction.reactions

import createTestGame
import gameactions.reactions.TakeHitReaction
import org.junit.Assert.*
import org.junit.Test


class TakeHitReactionTests {

    @Test
    fun test_canTake() {
        val game = createTestGame()
        val reaction = TakeHitReaction(game.players[0])

        assertTrue(reaction.canTake(game))
    }

    @Test
    fun test_reaction_kills() {
        val game = createTestGame()
        val reaction = TakeHitReaction(game.players[0])

        reaction.take(game)

        assertFalse(game.players[0].isAlive)
    }
}