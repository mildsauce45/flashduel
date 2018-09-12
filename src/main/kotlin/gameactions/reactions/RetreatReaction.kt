package gameactions.reactions

import engine.Direction
import engine.getPlayerLocation
import gameactions.MoveAction
import models.Card
import models.Game
import models.Player

class RetreatReaction(val player: Player, private val card: Card) : Reaction {
    override val cardsToDiscard: List<Card>
        get() = listOf(card)

    override fun canTake(game: Game): Boolean {
        val playerLocation = game.getPlayerLocation(player)

        return (playerLocation > 0 && player.orientation == Direction.RIGHT) ||
                (playerLocation < 17 && player.orientation == Direction.LEFT)
    }

    override fun take(game: Game) {
        // A retreat is essentially a move action followed by flagging the player as recovering
        MoveAction(player, card, true).takeAction(game)

        player.isRecovering = true
    }
}