package engine.strategies

import engine.getDistanceToClosestOpponent
import gameactions.*
import gameactions.reactions.*
import models.Card
import models.Game
import models.Player

// ** Bots turn (GameActions) **
// 1) Attacks if it can, always powering up with pairs, triples, etc
// 2) If adjacent to enemy, he pushes with the card it drew for the turn
// 3) If it can dash attack, using the card it drew for the turn as the dash, it does (powering up as much as possible)
// 4) Moves forward with the card it drew this turn
// ** Your turn (Reactions) **
// 1) When attacked or dash attacked, draws an extra card, then blocks, if possible
// 2) If it cannot block it retreats with the extra card it just drew
class TrainingDummyStrategy : PlayerStrategy {
    private lateinit var _thisTurnsCard: Card
    override lateinit var player: Player

    override fun startTurn(game: Game) {
        player.isRecovering = false

        _thisTurnsCard = game.deck.draw()

        player.draw(_thisTurnsCard)
    }

    override fun getNextAction(game: Game): GameAction {
        val attack = getAttackAction(game)
        val push = getPushAction()
        val dashAttack = getDashAttackIfAble(game)

        return when {
            attack.canTake(game) -> attack
            push.canTake(game) -> push
            dashAttack.canTake(game) -> dashAttack
            else -> MoveAction(player, _thisTurnsCard)
        }
    }

    override fun getReaction(action: GameAction, game: Game): Reaction {
        val extraCard = game.deck.draw()

        player.draw(extraCard)

        val attackCards = when (action) {
            is AttackAction -> action.cards
            is DashAttackAction -> action.attackCards
            else -> emptyList()
        }

        val block = BlockReaction(player, attackCards)
        val retreat = RetreatReaction(player, extraCard)

        return when {
            block.canTake(game) -> block
            retreat.canTake(game) -> retreat
            else -> TakeHitReaction(player)
        }
    }

    private fun getAttackAction(game: Game): AttackAction {
        val distanceToOpponent = player.getDistanceToClosestOpponent(game)

        return AttackAction(player, player.hand.filter { it!!.value == distanceToOpponent })
    }

    private fun getPushAction(): PushAction {
        return PushAction(player, _thisTurnsCard)
    }

    private fun getDashAttackIfAble(game: Game): DashAttackAction {
        val allCardsButThisTurns = player.hand.take(player.hand.size - 1)
        val distanceToClosestOpponent = player.getDistanceToClosestOpponent(game)

        val lookingForCardValue = distanceToClosestOpponent - _thisTurnsCard.value

        return DashAttackAction(player, _thisTurnsCard, allCardsButThisTurns.filter { it.value == lookingForCardValue })
    }
}