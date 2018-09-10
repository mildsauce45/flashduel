package gameactions.reactions

import models.Card
import models.Game
import models.Player

class BlockReaction(val player: Player, private val attackCards: List<Card>): Reaction {
    override val cardsToDiscard: List<Card>
        get() = player.hand.filter { it.value == attackCards[0].value }.take(attackCards.size)

    override fun canTake(game: Game): Boolean {
        val attackValue = attackCards[0].value

        return player.hand.count { it.value == attackValue } > attackCards.size
    }

    override fun take(game: Game) {
        // There's to do at this point
    }
}