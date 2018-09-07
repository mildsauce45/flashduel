package engine.strategies

import createTestGame
import gameactions.MoveAction
import models.Game
import models.Player
import org.junit.Assert.*
import org.junit.Test

class TrainingDummyStrategyTests {

    @Test
    fun test_draws_card_at_start_of_turn() {
        val game = setupTrainingDummyGame()
        val currentHandSize = game.currentPlayer.hand.size
        val deckSize = game.deck.remaining

        assertEquals(currentHandSize, Player.INITIAL_HAND_SIZE)

        game.currentPlayer.strategy.startTurn(game)

        assertEquals(game.currentPlayer.hand.size, currentHandSize + 1)
        assertEquals(deckSize, game.deck.remaining + 1)
    }

    @Test
    fun test_moves_with_card_drawn_this_turn() {
        val game = setupTrainingDummyGame()
        val topCard = game.deck.peek()

        game.currentPlayer.strategy.startTurn(game) // Draws the top card
        val action = game.currentPlayer.strategy.getNextAction(game)

        when (action){
            is MoveAction -> assertEquals(topCard, action.cardsToDiscard[0])
            else -> fail("Should be a move action here")
        }
    }

    private fun setupTrainingDummyGame(): Game {
        val game = createTestGame(listOf(HumanStrategy(), TrainingDummyStrategy()))

        game.start()

        // The dummy may not be the first player to go, so let's make sure it is
        if (game.currentPlayer.strategy is HumanStrategy)
            game.switchPlayer()

        return game
    }
}