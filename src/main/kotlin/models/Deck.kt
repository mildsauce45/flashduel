package models

import java.util.*

class Deck {
    companion object {
        const val CARDS_PER_NUM = 5
    }

    private val _cards = Vector<Card>()

    val remaining
        get() = _cards.size

    init {
        getCardList()
        shuffle()
    }

    fun draw(): Card {
        if (remaining > 0)
            return _cards.removeAt(0)

        throw IllegalStateException("Cannot draw from an empty deck")
    }

    /** This should only be used for testing purposes right now **/
    fun peek(): Card {
        if (remaining > 0)
            return _cards[0]

        throw IllegalStateException("Cannot peek at an empty deck")
    }

    private fun getCardList() {
        for (i in 1..Card.MAX_VALUE) {
            for (j in 1..Deck.CARDS_PER_NUM) {
                _cards.add(Card(i))
            }
        }
    }

    private fun shuffle() {
        val random = Random()

        for (i in _cards.size - 1 downTo 1) {
            val j = random.nextInt(i + 1)

            val temp = _cards[j]
            _cards[j] = _cards[i]
            _cards[i] = temp
        }
    }
}