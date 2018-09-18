package models

import java.util.*

class Game(val players: List<Player>, val deck: Deck = Deck(), val board: Board = Board()) {
    private var _currentPlayerIndex = -1
    private var _gameState: GameState = GameState.START_GAME

    val discardPile = Vector<Card>()
    var isDraw = false

    val currentPlayer: Player
        get() = players[_currentPlayerIndex]

    val currentState: GameState
        get() = _gameState

    val isGameOver
        get() = players.any { !it.isAlive } || isDraw

    val isTimeOver
        get() = deck.remaining == 0

    fun switchPlayer() {
        _currentPlayerIndex = (_currentPlayerIndex + 1) % players.size
    }

    fun start() {
        if (_currentPlayerIndex == -1) {
            // Draw everyone's cards
            val random = Random()

            // Pick the current player
            _currentPlayerIndex = random.nextInt(players.size)

            for (i in 1..Player.INITIAL_HAND_SIZE) {
                for (pi in 0 until players.size) {
                    players[pi].draw(deck.draw())
                }
            }
        }
    }

    fun stateTransition(newState: GameState) {
        if (currentState != newState)
            _gameState = newState
    }
}