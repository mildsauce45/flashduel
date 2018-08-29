package models

data class Card(val value: Int) {
    companion object {
        const val MAX_VALUE = 5
    }
}