package engine

import createPlayersFromStrategies
import createTestGame
import engine.strategies.TrainingDummyStrategy
import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test

class GamePlayerTests {
    @Test
    @Ignore
    fun test_plays_game() {
        val game = createTestGame {
            createPlayersFromStrategies(listOf(TrainingDummyStrategy(), TrainingDummyStrategy()))
        }

        val gamePlayer = GamePlayer(game)

        gamePlayer.play()

        assertTrue(game.isGameOver)
    }
}