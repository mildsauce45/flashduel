package gameactions

import engine.*
import models.Card
import models.Game
import models.Player

class MoveAction(private val player: Player, private val card: Card, private val isRetreat: Boolean = false) : GameAction {
    override val cardsToDiscard: List<Card>
        get() = listOf(card)

    override val requiresReaction: Boolean
        get() = false

    override fun canTake(game: Game): Boolean {
        val opponentIndices = game.getOpponentIndices(player)
        val playerIndex = game.getPlayerIndex(player)

        // Check if we're pinned on either side
        if (pinnedLeft(game, playerIndex, opponentIndices) || pinnedRight(game, playerIndex, opponentIndices))
            return false

        // Check if the opponent is right next to us in the direction of our move
        if (isOpponentAdjacent(game, playerIndex, opponentIndices))
            return false

        return true
    }

    override fun takeAction(game: Game) {
        val playerLocation = game.getPlayerLocation(player)
        val opponentLocations = game.getOpponentLocations(player)

        // In a game with multiple opponents, we can only move as far as the closest
        val nearestOpponent = when (player.orientation) {
            Direction.LEFT -> opponentLocations.filter { it < playerLocation }.max()
            else -> opponentLocations.filter { it > playerLocation }.min()
        } ?: throw IllegalStateException("There should always be a living opponent here, or the game would be over")

        val dirMultiplier = when (isRetreat) {
            true -> player.orientation.opposite()
            else -> player.orientation
        }.getMultiplier()

        var moveAmount = card.value * dirMultiplier
        val newLocation = playerLocation + moveAmount

        // If we would move past the player (or ont top of him) we need to calculate how much we need to adjust our movement
        moveAmount = when {
            newLocation >= nearestOpponent && player.orientation == Direction.RIGHT -> nearestOpponent - playerLocation - 1
            newLocation <= nearestOpponent && player.orientation == Direction.LEFT -> (playerLocation - nearestOpponent - 1) * -1
            else -> moveAmount
        }

        // Make the move
        game.board.movePlayer(game.getPlayerIndex(player), moveAmount)
    }

    private fun pinnedLeft(game: Game, playerIndex: Int, opponentIndices: List<Int>): Boolean {
        return game.board.playerPositions[playerIndex] == 0 && opponentIndices.any { game.board.playerPositions[it] == 1 }
    }

    private fun pinnedRight(game: Game, playerIndex: Int, opponentIndices: List<Int>): Boolean {
        return game.board.playerPositions[playerIndex] == game.board.track.size - 1 &&
            opponentIndices.any { game.board.playerPositions[it] == game.board.track.size - 2 }
    }

    private fun isOpponentAdjacent(game: Game, playerIndex: Int, opponentIndices: List<Int>): Boolean {
        val playerLocation = game.board.playerPositions[playerIndex]
        val opponentLocations = opponentIndices.map { game.board.playerPositions[it] }

        return when (game.players[playerIndex].orientation) {
            Direction.RIGHT -> opponentLocations.any { playerLocation + 1 == it }
            else -> opponentLocations.any { playerLocation - 1 == it }
        }
    }
}
