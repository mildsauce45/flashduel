package models

import org.junit.Test
import java.util.*

class DeckTests {
    @Test
    fun test_deck_starts_with_25_cards() {
        val deck = Deck()

        assert(deck.remaining == (Card.MAX_VALUE * Deck.CARDS_PER_NUM))
    }

    @Test
    fun test_draw_removes_card() {
        val deck = Deck()
        val startDeckSize = deck.remaining
        deck.draw()

        assert(startDeckSize == deck.remaining + 1)
    }

    @Test
    fun test_deck_has_proper_composition() {
        val deck = Deck()

        val allCards = Vector<Card>()

        // Draw the deck
        while (deck.remaining > 0)
            allCards.add(deck.draw())

        // Verify the max value is obeyed
        assert(allCards.map { it.value }.max() == Card.MAX_VALUE)

        // Verify we have the proper number of each card
        for (i in 1..Card.MAX_VALUE)
            assert(allCards.count { it.value == i } == Deck.CARDS_PER_NUM)
    }

    @Test
    fun test_deck_is_shuffled() {
        val deck = Deck()

        val unshuffledDeck = Vector<Card>()
        for (i in 1..Card.MAX_VALUE) {
            for (j in 1..Deck.CARDS_PER_NUM) {
                unshuffledDeck.add(Card(i))
            }
        }

        var shuffled = false
        while (!shuffled) {
            val unshuffledCard = unshuffledDeck.removeAt(0)
            val deckCard = deck.draw()

            if (unshuffledCard != deckCard)
                shuffled = true
        }

        assert(shuffled)
    }
}