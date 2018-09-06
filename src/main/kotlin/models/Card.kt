package models

data class Card(val value: Int) {
    companion object {
        const val MAX_VALUE = 5
    }

    init {
        if (value > MAX_VALUE)
            throw IllegalArgumentException("Card value exceeds max value of $MAX_VALUE")

        if (value < 1)
            throw IllegalArgumentException("Card value cannot be less than 1")
    }
}