package gameactions

import models.Card
import models.Game

interface GameAction {
    val cardsToDiscard: List<Card>
    val requiresReaction: Boolean

    fun canTake(game: Game): Boolean
    fun takeAction(game: Game)
}