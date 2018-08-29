package models

class Game(val players: List<Player>, val deck: Deck = Deck(), val board: Board = Board()) {
    private var _currentPlayerIndex = 0

    val currentPlayer: Player
        get() = players[_currentPlayerIndex]

    val isGameOver
        get() = players.any { !it.isAlive }

    fun switchPlayer() {
        _currentPlayerIndex = (_currentPlayerIndex + 1) % players.size
    }
}