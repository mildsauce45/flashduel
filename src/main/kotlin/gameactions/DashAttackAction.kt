package gameactions

import engine.getOpponentLocations
import engine.getPlayerLocation
import models.Card
import models.Game
import models.Player
import kotlin.math.abs

class DashAttackAction(private val player: Player, val dashCard: Card, val attackCards: List<Card>) : GameAction {
    override val cardsToDiscard: List<Card>
        get() = attackCards.plusElement(dashCard)

    override val requiresReaction: Boolean
        get() = true

    override fun canTake(game: Game): Boolean {
        val attackValue = attackCards.first().value

        if (attackValue == 1)
            return canDashNextTo(game)

        return dashCard.value + attackValue == getDistanceToClosestOpponent(game)
    }

    override fun takeAction(game: Game) {
        // The move action handles everything, including stopping at the opponent if you choose a dash card
        // greater than the space between you and the opponent
        val moveAction = MoveAction(player, dashCard)
        moveAction.takeAction(game)

        // The attack action handles the second part of the dash attack
        val attackAction = AttackAction(player, attackCards)
        attackAction.takeAction(game)
    }

    private fun canDashNextTo(game: Game): Boolean {
        // Since you can't dash through someone, we only care about the closest opponent
        val closestOpponentDistance = getDistanceToClosestOpponent(game)

        return closestOpponentDistance <= dashCard.value
    }

    private fun getDistanceToClosestOpponent(game: Game): Int {
        val playerLocation = game.getPlayerLocation(player)
        val opponentLocations = game.getOpponentLocations(player)

        return opponentLocations.map { abs(playerLocation - it) }.min()!!
    }
}