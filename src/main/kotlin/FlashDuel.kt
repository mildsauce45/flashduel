import engine.Direction
import engine.GamePlayer
import engine.strategies.HumanStrategy
import engine.strategies.TrainingDummyStrategy
import models.Game
import models.Player
import views.console.ConsoleView

class FlashDuel {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("hello")

            val game = Game(getPlayers())

            game.board.initGame(game.players)

            val view = ConsoleView(game)

            val player = GamePlayer(game, view)

            player.play()
        }

        private fun getPlayers(): List<Player> {
            return listOf(Player("Player 1", Direction.RIGHT, HumanStrategy()),
                    Player("Training Dummy", Direction.LEFT, TrainingDummyStrategy()))
        }
    }
}