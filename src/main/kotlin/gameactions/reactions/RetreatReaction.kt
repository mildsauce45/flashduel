package gameactions.reactions

import gameactions.MoveAction
import models.Card
import models.Game
import models.Player

class RetreatReaction(val player: Player, private val card: Card): Reaction {
    override val cardsToDiscard: List<Card>
        get() = listOf(card)

    override fun canTake(game: Game): Boolean {
        return player.hand.isNotEmpty()
    }

    override fun take(game: Game) {
        // A retreat is essentially a move action followed by flagging the player as recovering
        val moveAction = MoveAction(player, card)

        moveAction.takeAction(game)

        player.isRecovering = true
    }
}