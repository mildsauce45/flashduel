package models

data class Card(val value: Int) {
    companion object {
        const val MAX_VALUE = 5
    }

    init {
        if (value > MAX_VALUE)
            throw IllegalArgumentException("Card value exceeds max value of $MAX_VALUE")
    }
}