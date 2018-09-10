package engine.strategies

import engine.Direction
import engine.getDistanceToClosestOpponent
import engine.getOpponentLocations
import engine.getPlayerLocation
import gameactions.*
import models.Card
import models.Game
import kotlin.math.abs

// ** Bots turn **
// 1) Attacks if it can, always powering up with pairs, triples, etc
// 2) If adjacent to enemy, he pushes with the card it drew for the turn
// 3) If it can dash attack, using the card it drew for the turn as the dash, it does (powering up as much as possible)
// 4) Moves forward with the card it drew this turn
// ** Your turn **
// 1) When attacked or dash attacked, draws an extra card, then blocks, if possible
// 2) If it cannot block it retreats with the extra card it just drew
class TrainingDummyStrategy: PlayerStrategy {
    private lateinit var _thisTurnsCard: Card

    override fun startTurn(game: Game) {
        _thisTurnsCard = game.deck.draw()

        game.currentPlayer.draw(_thisTurnsCard)
    }

    override fun getNextAction(game: Game): GameAction {
        val cardsToAttackWith = getAttackCardsIfAble(game)
        if (cardsToAttackWith.isNotEmpty())
            return AttackAction(game.currentPlayer, cardsToAttackWith)

        if (isAdjacentToOpponent(game))
            return PushAction(game.currentPlayer, _thisTurnsCard)

        val (dashCard, dashAttackCards) = getDashAttackIfAble(game)
        if (dashCard != null && dashAttackCards.isNotEmpty())
            return DashAttackAction(game.currentPlayer, dashCard, dashAttackCards)

        return MoveAction(game.currentPlayer, _thisTurnsCard, Direction.LEFT)
    }

    private fun getAttackCardsIfAble(game: Game): List<Card> {
        val distanceToOpponent = game.currentPlayer.getDistanceToClosestOpponent(game)
        val distinctCardValues = game.currentPlayer.hand.map { it.value }.distinct()

        for (cv in distinctCardValues) {
            if (cv == distanceToOpponent)
                return game.currentPlayer.hand.filter { it.value == cv }
        }

        return emptyList()
    }

    private fun isAdjacentToOpponent(game: Game): Boolean {
        val playerLocation = game.getPlayerLocation(game.currentPlayer)
        val opponentLocations = game.getOpponentLocations(game.currentPlayer)

        return opponentLocations.any { abs(it - playerLocation) == 1 }
    }

    private fun getDashAttackIfAble(game: Game): Pair<Card?, List<Card>> {
        val allCardsButThisTurns = game.currentPlayer.hand.take(game.currentPlayer.hand.size - 1)
        val distanceToClosestOpponent = game.currentPlayer.getDistanceToClosestOpponent(game)

        val lookingForCardValue = distanceToClosestOpponent - _thisTurnsCard.value
        if (allCardsButThisTurns.any { it.value == lookingForCardValue})
            return Pair(_thisTurnsCard, allCardsButThisTurns.filter { it.value == lookingForCardValue })

        return Pair(null, emptyList()) // Cannot dash attack
    }
}