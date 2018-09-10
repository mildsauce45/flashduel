package gameactions.reactions

import models.Card
import models.Game

interface Reaction {
    val cardsToDiscard: List<Card>

    fun canTake(game: Game): Boolean
    fun take(game: Game)
}