package models

import createTestGame
import org.junit.Assert.*
import org.junit.Test

class GameTests {

    @Test
    fun test_start_game_works() {
        val game = createTestGame()

        assertEquals(game.currentState, GameState.START_GAME)

        game.start()

        assertEquals(game.players[0].hand.size, Player.INITIAL_HAND_SIZE)
        assertEquals(game.players[1].hand.size, Player.INITIAL_HAND_SIZE)

        // Now let's test that calling start twice accidentally doesn't really do anything
        val currentPlayer = game.currentPlayer
        val deckSize = game.deck.remaining

        game.start()

        assertEquals(game.currentPlayer, currentPlayer)
        assertEquals(game.deck.remaining, deckSize)
    }

    @Test
    fun test_game_ends_when_player_hit() {
        var game = createTestGame()
        assertFalse(game.isGameOver)

        game.players[0].takeHit()
        assertTrue(game.isGameOver)

        game = createTestGame()
        game.players[1].takeHit()
        assertTrue(game.isGameOver)
    }

    @Test
    fun test_switch_player_works() {
        val game = createTestGame()
        game.start()

        val startingPlayer = game.currentPlayer

        game.switchPlayer()
        assertNotEquals(game.currentPlayer, startingPlayer)

        game.switchPlayer()
        assertEquals(game.currentPlayer, startingPlayer)
    }
}