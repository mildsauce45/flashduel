import engine.Direction
import engine.strategies.PlayerStrategy
import models.Board
import models.Deck
import models.Game
import models.Player
import java.util.*

fun createTwoPlayers(): List<Player> {
    return listOf(Player("Player 1", Direction.RIGHT), Player("Player 2", Direction.LEFT))
}

fun createFourPlayers(): List<Player> {
    return listOf(
            Player("Player 1", Direction.RIGHT),
            Player("Player 2", Direction.LEFT),
            Player("Player 3", Direction.RIGHT),
            Player("Player 4", Direction.LEFT))
}

fun createTestGame(deck: Deck = Deck(), playersProducer: () -> List<Player> = ::createTwoPlayers): Game {
    val players = playersProducer()
    val board = Board()

    board.initGame(players)

    return Game(players, deck, board)
}

fun createPlayersFromStrategies(strategies: List<PlayerStrategy>): List<Player> {
    val players = ArrayList<Player>()

    for (i in 0 until strategies.size) {
        players.add(Player(
                "Player ${i + 1}",
                when (i % 2) { 0 -> Direction.RIGHT
                    else -> Direction.LEFT
                },
                strategies[i]))
    }

    return players
}