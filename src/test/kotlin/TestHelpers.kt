import models.Board
import models.Deck
import models.Game
import models.Player

fun createTwoPlayers(): List<Player> {
    return listOf(Player("Player 1"), Player("Player 2"))
}

fun createTestGame(deck: Deck = Deck(), board: Board = Board()): Game {
    val players = createTwoPlayers()

    board.initGame(players)

    return Game(players, deck, board)
}