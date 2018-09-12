package gameaction.reactions

import createTestGame
import gameactions.reactions.BlockReaction
import models.Card
import models.Game
import org.junit.Assert.*
import org.junit.Test

class BlockReactionTests {
    @Test
    fun test_canTake_player_has_card() {
        val game = setupGame(listOf(2))

        val reaction = BlockReaction(game.players[0], listOf(Card(2)))

        assertTrue(reaction.canTake(game))
    }

    @Test
    fun test_canTake_player_has_cards() {
        val game = setupGame(listOf(3, 3))

        val reaction = BlockReaction(game.players[0], listOf(Card(3), Card(3)))

        assertTrue(reaction.canTake(game))
    }

    @Test
    fun test_canTake_player_doesnt_have_card() {
        val game = setupGame(listOf(2))

        val reaction = BlockReaction(game.players[0], listOf(Card(3)))

        assertFalse(reaction.canTake(game))
    }

    @Test
    fun test_canTake_player_doesnt_have_enough_cards() {
        val game = setupGame(listOf(3))

        val reaction = BlockReaction(game.players[0], listOf(Card(3), Card(3)))

        assertFalse(reaction.canTake(game))
    }

    @Test
    fun test_canTake_player_doesnt_have_any_cards() {
        val game = setupGame(listOf(3))

        val reaction = BlockReaction(game.players[0], listOf(Card(4), Card(4)))

        assertFalse(reaction.canTake(game))
    }

    private fun setupGame(cards: List<Int>): Game {
        val game = createTestGame()

        for (c in cards)
            game.players[0].draw(Card(c))

        return game
    }
}