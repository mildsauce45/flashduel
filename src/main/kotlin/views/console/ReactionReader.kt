package views.console

import gameactions.reactions.BlockReaction
import gameactions.reactions.Reaction
import gameactions.reactions.RetreatReaction
import gameactions.reactions.TakeHitReaction
import models.Card
import models.Game
import models.Player

fun readReaction(player: Player, attackCards: List<Card>, game: Game): Reaction {
    while (true) {
        val blockReaction = BlockReaction(player, attackCards)
        if (blockReaction.canTake(game)) {
            println("You can block the attack do you want to (Y/N)?")

            val bool = readYN()
            if (bool)
                return blockReaction

            println("Retreat with what value card?")

            val card = readSingleCard(player)
            if (card != null) {
                val reaction = RetreatReaction(player, card)
                if (reaction.canTake(game))
                    return reaction
            }

            return TakeHitReaction(player)
        }
    }
}

private fun readYN(): Boolean {
    while (true) {
        val input = readLine()

        when (input) {
            "y" -> return true
            "Y" -> return true
            "n" -> return false
            "N" -> return false
        }

        println("Invalid format")
    }
}