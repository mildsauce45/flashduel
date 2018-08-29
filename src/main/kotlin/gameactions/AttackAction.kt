package gameactions

import engine.getPlayerIndex
import models.Card
import models.Game
import models.Player

class AttackAction(val player: Player, val cards: List<Card>): GameAction {
    override fun canTake(game: Game): Boolean {
        //val playerIndex = game.getPlayerIndex(player)

        return true
    }

    override fun takeAction(game: Game) {

    }

}