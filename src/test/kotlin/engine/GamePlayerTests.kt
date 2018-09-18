package engine

import createPlayersFromStrategies
import createTestGame
import engine.strategies.TrainingDummyStrategy
import models.Game
import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test
import views.GameView

class GamePlayerTests {
    @Test
    fun test_plays_game() {
        val game = createTestGame {
            createPlayersFromStrategies(listOf(TrainingDummyStrategy(), TrainingDummyStrategy()))
        }

        val gamePlayer = GamePlayer(game, TestViewer(game))

        gamePlayer.play()

        assertTrue(game.isGameOver)
        assertTrue(game.isDraw || game.players.count { !it.isAlive } == 1)
    }
}

class TestViewer(override val game: Game): GameView {

    override fun display() {

    }

    override fun showMessage(msg: String) {

    }

}