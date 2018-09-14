package gameactions

import engine.discard
import engine.getOpponentLocations
import engine.getOpponents
import engine.getPlayerLocation
import models.Card
import models.Game
import models.Player
import kotlin.math.abs

class AttackAction(override val player: Player, val cards: List<Card>): GameAction {
    override val cardsToDiscard: List<Card>
        get() = cards

    override fun canTake(game: Game): Boolean {
        if (cards.isEmpty())
            return false

        val playerLocation = game.getPlayerLocation(player)
        val opponentLocations = game.getOpponentLocations(player)
        val distances = opponentLocations.map { abs(playerLocation - it) }

        return distances.any { it == cards.first().value }
    }

    override fun takeAction(game: Game): Player? {
        // For now just take the first player at that spot
        val playerLocation = game.getPlayerLocation(player)
        val opponents = game.getOpponents(player)
        val attackValue = cards[0].value

        // Discard the cards used in the attack
        player.discard(cardsToDiscard, game)

        // Let this throw if the list is actually empty because we shouldn't have gotten here any other way
        return opponents.first{ abs(game.getPlayerLocation(it) - playerLocation) == attackValue }
    }
}
