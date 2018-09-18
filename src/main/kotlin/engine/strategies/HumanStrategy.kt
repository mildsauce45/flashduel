package engine.strategies

import gameactions.GameAction
import gameactions.RequiresReaction
import gameactions.reactions.Reaction
import models.Game
import models.Player
import views.console.displayHand
import views.console.readGameAction
import views.console.readReaction

class HumanStrategy: PlayerStrategy {

    override lateinit var player: Player

    override fun startTurn(game: Game) {
        // Humans currently don't get to do anything fun on the start of their turn
        // When abilities are added, they'll get refreshed here
        player.isRecovering = false
    }

    override fun getNextAction(game: Game): GameAction {
        // TODO: Not assume we're getting this from command line
        while (true) {
            displayHand(player)

            val action = readGameAction(player)

            if (action.canTake(game))
                return action
            else
                println("Cannot take that action")
        }
    }

    override fun getReaction(action: RequiresReaction, game: Game): Reaction {
        // TODO: Not assume we're getting this form the command line
        while (true) {
            displayHand(player)

            val reaction = readReaction(player, action.attackCards, game)

            if (reaction.canTake(game))
                return reaction
            else
                println("Cannot take that reaction")
        }
    }
}
