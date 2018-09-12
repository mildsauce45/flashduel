package gameactions.reactions

import models.Card
import models.Game
import models.Player

class TakeHitReaction(val player: Player): Reaction {
    override val cardsToDiscard: List<Card>
        get() = emptyList()

    override fun canTake(game: Game): Boolean {
        return true
    }

    override fun take(game: Game) {
        player.takeHit()
    }
}