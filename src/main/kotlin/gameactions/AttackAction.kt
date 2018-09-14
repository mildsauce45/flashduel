package gameactions

import engine.getOpponentLocations
import engine.getOpponents
import engine.getPlayerLocation
import models.Card
import models.Game
import models.Player
import kotlin.math.abs

class AttackAction(private val player: Player, val cards: List<Card>): GameAction {
    override val cardsToDiscard: List<Card>
        get() = cards

    override val requiresReaction: Boolean
        get() = true

    override fun canTake(game: Game): Boolean {
        if (cards.isEmpty())
            return false

        val playerLocation = game.getPlayerLocation(player)
        val opponentLocations = game.getOpponentLocations(player)
        val distances = opponentLocations.map { abs(playerLocation - it) }

        return distances.any { it == cards.first().value }
    }

    override fun takeAction(game: Game) {
        // TODO: Anything to do here?
    }
}
