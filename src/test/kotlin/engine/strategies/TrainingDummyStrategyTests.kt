package engine.strategies

import createPlayersFromStrategies
import createTestGame
import gameactions.*
import models.*
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
    fun test_attacks_if_able() {
        val game = setupTrainingDummyGame(listOf(2, 2, 3, 3, 4, 4, 5, 5, 2, 1, 3))
        val topCard = game.deck.peek()

        game.board.movePlayer(0, 7)
        game.board.movePlayer(1, -7)

        game.currentPlayer.strategy.startTurn(game)

        var action = game.currentPlayer.strategy.getNextAction(game)

        when (action) {
            is AttackAction -> {
                assertEquals(topCard, action.cards.first())
                assertEquals(2, action.cards.size)
            }
            else -> fail("Should be attacking with two 3's")
        }

        game.board.movePlayer(1, numberSpaces = -1)

        action = game.currentPlayer.strategy.getNextAction(game)

        when (action) {
            is AttackAction -> {
                assertEquals(Card(2), action.cards.first())
                assertEquals(1, action.cards.size)
            }
            else -> fail("Should be attacking with one two")
        }
    }

    @Test
    fun test_pushes_when_adjacent() {
        val game = setupTrainingDummyGame(listOf(2, 2, 3, 3, 4, 4, 5, 5, 2, 2, 4))
        val topCard = game.deck.peek()

        game.board.movePlayer(0, 8)
        game.board.movePlayer(1, -8)

        game.currentPlayer.strategy.startTurn(game)
        val action = game.currentPlayer.strategy.getNextAction(game)

        when (action) {
            is PushAction -> assertEquals(topCard, action.cardsToDiscard[0])
            else -> fail("When adjacent we should be pushing")
        }
    }

    @Test
    fun test_dash_attacks_with_card_drawn() {
        val game = setupTrainingDummyGame(listOf(1, 1, 3, 3, 4, 4, 5, 5, 4, 4, 2))
        val topDeck = game.deck.peek()

        game.board.movePlayer(0, 4)
        game.board.movePlayer(1, -7)

        game.currentPlayer.strategy.startTurn(game)

        val action = game.currentPlayer.strategy.getNextAction(game)

        when (action) {
            is DashAttackAction -> {
                assertEquals(topDeck, action.dashCard)
                assertEquals(2, action.attackCards.size) // We have two 4's
                assertEquals(4, action.attackCards[0].value)
            }
            else -> fail("We should dash attack by moving 2 spaces and attacking with 2 4's")
        }
    }

    @Test
    fun test_moves_with_card_drawn_this_turn() {
        val game = setupTrainingDummyGame()
        val topCard = game.deck.peek()

        game.currentPlayer.strategy.startTurn(game) // Draws the top card
        val action = game.currentPlayer.strategy.getNextAction(game)

        when (action) {
            is MoveAction -> assertEquals(topCard, action.cardsToDiscard[0])
            else -> fail("Should be a move action here")
        }
    }

    private fun setupTrainingDummyGame(deck: List<Int> = emptyList()): Game {
        val game = createTestGame(Deck(deck.map { Card(it) })) {
            createPlayersFromStrategies(listOf(HumanStrategy(), TrainingDummyStrategy()))
        }

        game.start()

        // The dummy may not be the first player to go, so let's make sure it is
        if (game.currentPlayer.strategy is HumanStrategy)
            game.switchPlayer()

        return game
    }
}