package models

import engine.strategies.HumanStrategy
import engine.strategies.PlayerStrategy
import java.util.*

class Player(val name: String, val strategy: PlayerStrategy = HumanStrategy()) {
    companion object {
        const val INITIAL_HAND_SIZE = 5
    }

    private var hitsRemaining = 1

    var isRecovering = false
    val hand: Vector<Card> = Vector(5)

    val isAlive
        get() = hitsRemaining > 0

    val canAct
        get() = !isRecovering

    fun draw(card: Card) {
        hand.add(card)
    }

    fun takeHit() {
        hitsRemaining -= 1
    }
}