package engine

import models.Game
import models.Player

fun Game.getPlayerIndex(player: Player): Int {
    return this.players.indexOf(player)
}

fun Game.getOpponents(player: Player): List<Player> {
    val playerIndex = this.getPlayerIndex(player)

    return when {
        playerIndex % 2 == 0 -> this.players.filter { this.getPlayerIndex(it) % 2 == 1 }
        else -> this.players.filter { this.getPlayerIndex(it) % 2 == 0 }
    }
}

fun Game.getOpponentIndices(player: Player): List<Int> {
    return this.getOpponents(player).map { this.getPlayerIndex(it) }
}

fun Game.getPlayerLocation(player: Player): Int {
    return this.board.playerPositions[this.getPlayerIndex(player)]
}

fun Game.getOpponentLocations(player: Player): List<Int> {
    return this.getOpponentIndices(player).map { this.board.playerPositions[it] }
}