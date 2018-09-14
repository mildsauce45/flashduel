package engine

import models.Card
import models.Game
import models.Player
import kotlin.math.abs

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

fun Player.getDistanceToClosestOpponent(game: Game): Int {
    val playerLocation = game.getPlayerLocation(this)
    val opponentLocations = game.getOpponentLocations(this)

    return opponentLocations.map { abs(playerLocation - it) }.min()!!
}

fun Player.discard(cards: List<Card>, game: Game) {
    for (c in cards) {
        this.hand.remove(c)
        game.discardPile.add(c)
    }
}

fun Player.drawToFive(game: Game) {
    while (this.hand.size < 5 && game.deck.remaining > 0)
        this.draw(game.deck.draw())
}