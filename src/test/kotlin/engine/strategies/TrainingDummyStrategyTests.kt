package engine.strategies

import createPlayersFromStrategies
import createTestGame
import gameactions.*
import gameactions.reactions.*
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

    @Test
    fun test_blocks_if_able() {
        val game = setupTrainingDummyGame(listOf(2, 2, 3, 3, 4, 4, 5, 5, 2, 3, 1, 1))

        val attackWithOneCard = AttackAction(game.players[0], listOf(Card(4)))
        var reaction = game.currentPlayer.strategy.getReaction(attackWithOneCard, game)

        when (reaction) {
            is BlockReaction -> assertEquals(reaction.cardsToDiscard.size, 1)
            else -> fail("Should be able to block the attack")
        }

        val attackWithMultipleCards = AttackAction(game.players[0], listOf(Card(3), Card(3)))
        reaction = game.currentPlayer.strategy.getReaction(attackWithMultipleCards, game)

        when (reaction) {
            is BlockReaction -> assertEquals(reaction.cardsToDiscard.size, 2)
            else -> fail("Should be able to block the multi-card attack")
        }
    }

    @Test
    fun test_retreats_if_cannot_block() {
        val game = setupTrainingDummyGame(listOf(2, 2, 3, 3, 4, 5, 5, 5, 2, 3, 1, 1))
        val topCard = game.deck.peek()

        // Move in because you cannot retreat from the far right or left edges
        game.board.movePlayer(1, -2)

        val attackWithOneCard = AttackAction(game.players[0], listOf(Card(4)))
        var reaction = game.currentPlayer.strategy.getReaction(attackWithOneCard, game)

        when (reaction) {
            is RetreatReaction -> assertEquals(reaction.cardsToDiscard[0], topCard)
            else -> fail("Should retreat because we don't have a 4")
        }

        val attackWithMultipleCards = AttackAction(game.players[0], listOf(Card(2), Card(2)))
        reaction = game.currentPlayer.strategy.getReaction(attackWithMultipleCards, game)

        when (reaction) {
            is RetreatReaction -> assertEquals(reaction.cardsToDiscard[0], topCard)
            else -> fail("Should retreat because we don't have two 2s")
        }
    }

    @Test
    fun test_takes_hit_if_cannot_block_or_retreat() {
        val game = setupTrainingDummyGame(listOf(2, 2, 3, 3, 4, 5, 5, 5, 2, 3, 1, 1))

        val attackWithOneCard = AttackAction(game.players[0], listOf(Card(4)))
        val reaction = game.currentPlayer.strategy.getReaction(attackWithOneCard, game)

        when (reaction) {
            is TakeHitReaction -> assertTrue(true)
            else -> fail("We don't have a 4 and we're at the edge, we should lose")
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