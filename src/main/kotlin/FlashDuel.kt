import engine.Direction
import engine.GamePlayer
import engine.strategies.HumanStrategy
import engine.strategies.RookStrategy
import engine.strategies.TrainingDummyStrategy
import models.Game
import models.Player
import models.abilities.jaina.SmolderingEmbers
import models.abilities.rook.Thunderclap
import models.abilities.rook.WindmillCrusher
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
            return listOf(Player("Player 1", Direction.RIGHT, HumanStrategy(), listOf(SmolderingEmbers())), getRook())
        }

        private fun getTrainingDummy(): Player {
            return Player("Training Dummy", Direction.LEFT, TrainingDummyStrategy())
        }

        private fun getRook(): Player {
            return Player("Rook", Direction.LEFT, RookStrategy(), listOf(Thunderclap(), WindmillCrusher()))
        }
    }
}