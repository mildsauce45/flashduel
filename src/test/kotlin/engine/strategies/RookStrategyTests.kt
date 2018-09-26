package engine.strategies

import createTestGame
import engine.Direction
import gameactions.AttackAction
import gameactions.DashAttackAction
import models.*
import models.abilities.rook.*
import org.junit.Assert.*
import org.junit.Test

class RookStrategyTests {
    @Test
    fun test_uses_thunderclap() {
        val game = createRookTestGame(listOf(2, 1, 2, 1, 3, 3, 3, 3, 3, 3, 4))

        game.board.movePlayer(0, 10)
        game.board.movePlayer(1, -2)

        game.currentPlayer.strategy.startTurn(game)
        game.stateTransition(GameState.GET_ACTION)

        val action = game.currentPlayer.strategy.getNextAction(game)

        when (action) {
            is DashAttackAction -> assertFalse(action.canRetreat)
            else -> fail("We should have gotten a dash attack here")
        }

        val tc = game.currentPlayer.abilities.first { it is Thunderclap }

        assertTrue(tc.usedThisTurn)
    }

    @Test
    fun test_uses_windmill_crusher() {
        val game = createRookTestGame(listOf(2, 1, 2, 1, 3, 3, 3, 3, 3, 3, 4))

        game.board.movePlayer(0, 10)
        game.board.movePlayer(1, -6)

        game.currentPlayer.strategy.startTurn(game)
        game.stateTransition(GameState.GET_ACTION)

        val action = game.currentPlayer.strategy.getNextAction(game)

        when (action) {
            is AttackAction -> assertFalse(action.isBlockable)
            else -> fail("Should have an attack action here")
        }

        val wc = game.currentPlayer.abilities.first { it is WindmillCrusher }

        assertTrue(wc.usedThisTurn)
    }

    private fun createRookTestGame(deck: List<Int> = emptyList()): Game {
        val game = createTestGame(Deck(deck.map { Card(it) })) {
            createRookGamePlayers()
        }

        game.start()

        // Rook may not be the first player to go, so let's make sure it is
        if (game.currentPlayer.strategy is HumanStrategy)
            game.switchPlayer()

        return game
    }

    private fun createRookGamePlayers(): List<Player> {
        return listOf(
                Player("Player 1", Direction.RIGHT, HumanStrategy()),
                Player("Rook", Direction.LEFT, RookStrategy(), listOf(Thunderclap(), WindmillCrusher(), RockArmor())))
    }
}