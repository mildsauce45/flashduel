package gameactions

import engine.getOpponentLocations
import engine.getOpponents
import engine.getPlayerIndex
import engine.getPlayerLocation
import models.Card
import models.Game
import models.Player
import kotlin.math.abs

class PushAction(private val player: Player, private val card: Card): GameAction {
    override fun canTake(game: Game): Boolean {
        val playerLocation = game.getPlayerLocation(player)
        val opponentLocations = game.getOpponentLocations(player)
        val distances = opponentLocations.map { abs(it - playerLocation) }

        return distances.any { it == 1 }
    }

    override fun takeAction(game: Game) {
        val playerLocation = game.getPlayerLocation(player)

        val targetedOpponent = game.getOpponents(player).first {
            abs(game.getPlayerLocation(it) - playerLocation) == 1
        }

        val movementMultiplier = if (game.getPlayerLocation(targetedOpponent) > playerLocation) 1 else  -1

        game.board.movePlayer(game.getPlayerIndex(targetedOpponent), card.value * movementMultiplier)
    }
}