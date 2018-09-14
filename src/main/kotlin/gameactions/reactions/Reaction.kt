package gameactions.reactions

import models.Card
import models.Game
import models.Player

interface Reaction {
    val player: Player
    val cardsToDiscard: List<Card>

    fun canTake(game: Game): Boolean
    fun take(game: Game)
}