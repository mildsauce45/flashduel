package gameactions

import engine.getOpponentLocations
import engine.getPlayerLocation
import models.Card
import models.Game
import models.Player
import kotlin.math.abs

class DashAttackAction(override val player: Player, val dashCard: Card, override val attackCards: List<Card>) : GameAction, RequiresReaction {
    override val cardsToDiscard: List<Card>
        get() = attackCards.plusElement(dashCard)

    override val asMessage: String
        get() = "${player.name} dashes with a ${dashCard.value} and attacks with ${attackCards.size} ${attackCards[0].value}'s"

    override fun canTake(game: Game): Boolean {
        if (attackCards.isEmpty())
            return false

        val attackValue = attackCards.first().value

        if (attackValue == 1)
            return canDashNextTo(game)

        return dashCard.value + attackValue == getDistanceToClosestOpponent(game)
    }

    override fun takeAction(game: Game): Player? {
        // The move action handles everything, including stopping at the opponent if you choose a dash card
        // greater than the space between you and the opponent
        val moveAction = MoveAction(player, dashCard)
        moveAction.takeAction(game)

        // The attack action handles the second part of the dash attack
        val attackAction = AttackAction(player, attackCards)
        return attackAction.takeAction(game)
    }

    private fun canDashNextTo(game: Game): Boolean {
        // Since you can't dash through someone, we only care about the closest opponent
        val closestOpponentDistance = getDistanceToClosestOpponent(game)

        return closestOpponentDistance - 1 <= dashCard.value
    }

    private fun getDistanceToClosestOpponent(game: Game): Int {
        val playerLocation = game.getPlayerLocation(player)
        val opponentLocations = game.getOpponentLocations(player)

        return opponentLocations.map { abs(playerLocation - it) }.min()!!
    }
}
