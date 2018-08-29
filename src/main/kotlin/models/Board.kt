package models

class Board {
    private val _startingPositions = arrayListOf(0, 17, 0, 17)
    private var _numberOfPlayers: Int = 0

    val track: List<CellType> = listOf(
            CellType.B, CellType.B, CellType.W, CellType.W, CellType.B, CellType.B, CellType.W, CellType.W,
            CellType.B, CellType.B, CellType.W, CellType.W, CellType.B, CellType.B, CellType.W, CellType.W,
            CellType.B, CellType.B)

    val playerPositions = arrayListOf(-1, -1, -1, -1)

    fun initGame(players: List<Player>) {
        _numberOfPlayers = players.size

        for (i in 0..3 /*max players - 1*/) {
            if (i < players.size) {
                playerPositions[i] = _startingPositions[i]
            }
        }
    }

    fun movePlayer(playerIndex: Int, numberSpaces: Int) {
        if (playerIndex < 0 || playerIndex >= _numberOfPlayers)
            throw IllegalArgumentException("$playerIndex exceeds the number of players $_numberOfPlayers")

        // Move the player and clamp the player (Collision logic with other players should be handled by the engine)
        playerPositions[playerIndex] = (playerPositions[playerIndex] + numberSpaces).clamp(0, track.size - 1)
    }

    private fun Int.clamp(min: Int, max: Int): Int {
        return when {
            this < min -> min
            this > max -> max
            else -> this
        }
    }
}

enum class CellType {
    B,
    W
}
