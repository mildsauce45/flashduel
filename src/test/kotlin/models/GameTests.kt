package models

import createTestGame
import org.junit.Test

class GameTests {
    @Test
    fun test_first_player_is_current_player() {
        val game = createTestGame()
        assert(game.players[0] == game.currentPlayer)
    }

    @Test
    fun test_game_ends_when_player_hit() {
        var game = createTestGame()
        assert(!game.isGameOver)

        game.players[0].takeHit()
        assert(game.isGameOver)

        game = createTestGame()
        game.players[1].takeHit()
        assert(game.isGameOver)
    }

    @Test
    fun test_switch_player_works() {
        val game = createTestGame()

        game.switchPlayer()
        assert(game.currentPlayer == game.players[1])

        game.switchPlayer()
        assert(game.currentPlayer == game.players[0])
    }
}