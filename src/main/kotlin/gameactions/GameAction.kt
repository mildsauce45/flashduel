package gameactions

import models.Card
import models.Game
import models.Player

interface GameAction {
    val player: Player
    val cardsToDiscard: List<Card>
    val asMessage: String

    fun canTake(game: Game): Boolean
    fun takeAction(game: Game): Player?
}