package models

import org.junit.Test

class BoardTests {
    @Test
    fun test_two_player_game_initialized_properly() {
        val board = Board()
        board.initGame(createTwoPlayers())

        assert(board.playerPositions[0] == 0)
        assert(board.playerPositions[1] == board.track.size - 1)
        assert(board.playerPositions[2] == -1)
        assert(board.playerPositions[3] == -1)
    }

    @Test
    fun test_four_player_game_initialized_properly() {
        val board = Board()
        board.initGame(createFourPlayers())

        assert(board.playerPositions[0] == 0)
        assert(board.playerPositions[1] == board.track.size - 1)
        assert(board.playerPositions[2] == 0)
        assert(board.playerPositions[3] == board.track.size - 1)
    }

    @Test
    fun test_move_player() {
        val board = Board()
        board.initGame(createTwoPlayers())

        board.movePlayer(0, -1)
        assert(board.playerPositions[0] == 0)

        board.movePlayer(0, 25)
        assert(board.playerPositions[0] == board.track.size - 1)

        board.movePlayer(1, -3)
        assert(board.playerPositions[1] == 14) // Because the board is not responsible for collision detection
    }

    private fun createTwoPlayers(): List<Player> {
        return listOf(Player("Player 1"), Player("Player 2"))
    }

    private fun createFourPlayers(): List<Player> {
        return listOf(Player("Player 1"), Player("Player 2"), Player("Player 3"), Player("Player 4"))
    }
}