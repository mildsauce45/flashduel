package models

import engine.strategies.HumanStrategy
import engine.strategies.PlayerStrategy
import engine.Direction
import models.abilities.Ability
import java.util.*

class Player(val name: String, val orientation: Direction, val strategy: PlayerStrategy = HumanStrategy(), val abilities: List<Ability> = emptyList()) {
    companion object {
        const val INITIAL_HAND_SIZE = 5
    }

    init {
        strategy.player = this
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

    fun restoreAbilities() {
        for (a in abilities)
            a.usedThisTurn = false
    }
}