package gameactions

import models.Game

interface GameAction {
    fun canTake(game: Game): Boolean
    fun takeAction(game: Game)
}