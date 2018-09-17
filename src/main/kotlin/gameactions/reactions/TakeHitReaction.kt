package gameactions.reactions

import models.Card
import models.Game
import models.Player

class TakeHitReaction(override val player: Player): Reaction {
    override val cardsToDiscard: List<Card>
        get() = emptyList()

    override val asMessage: String
        get() = "${player.name} takes the hit"

    override fun canTake(game: Game): Boolean {
        return true
    }

    override fun take(game: Game) {
        player.takeHit()
    }
}