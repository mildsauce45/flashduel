package views.console

import gameactions.*
import models.Card
import models.Player
import util.filterOutFirst

fun displayHand(player: Player) {
    print("${player.name}'s hand: ")

    for (c in player.hand.sortedBy { it.value })
        print("${c.value} ")

    println()
}

fun readGameAction(player: Player): GameAction {
    var gameAction: GameAction? = null

    while (gameAction == null) {
        displayGameActionMenu()

        val actionType = readDesiredAction()
        gameAction = when (actionType) {
            1 -> readAttackAction(player)
            2 -> readDashAttackAction(player)
            3 -> readMoveAction(player)
            else -> readPushAction(player)
        }
    }

    return gameAction
}

private fun displayGameActionMenu() {
    println("1) Attack")
    println("2) Dash Attack")
    println("3) Move")
    println("4) Push")
}

private fun readDesiredAction(): Int {
    while (true) {
        print("Action (1..4): ")
        val input = readInt()
        if (input in 1..4)
            return input

        println("Invalid value: $input")
    }
}

private fun readAttackAction(player: Player): GameAction? {
    while (true) {
        println("Attack with what value card?")

        val attackCards = readAttackCards(player)
        if (attackCards.isNotEmpty())
            return AttackAction(player, attackCards)
    }
}

private fun readDashAttackAction(player: Player): GameAction? {
    while (true) {
        println("Dash with what value card")

        val dashCard = readSingleCard(player)
        if (dashCard != null) {
            val attackCards = readAttackCards(player, dashCard)
            if (attackCards.isNotEmpty())
                return DashAttackAction(player, dashCard, attackCards)
        }
    }
}

private fun readMoveAction(player: Player): GameAction? {
    while (true) {
        println("Move with what value card?")

        val card = readSingleCard(player)

        if (card != null)
            return MoveAction(player, card)
    }
}

private fun readPushAction(player: Player): GameAction? {
    while(true) {
        println("Push with what value card?")

        val card = readSingleCard(player)

        if (card != null)
            return PushAction(player, card)
    }
}

private fun readInt(): Int {
    while (true) {
        val input = readLine()

        val convertedInput = input?.toIntOrNull()

        if (convertedInput != null)
            return convertedInput

        println("Invalid numeric input")
    }
}

private fun readSingleCard(player: Player): Card? {
    val cardValue = readInt()

    return player.hand.firstOrNull { it.value == cardValue }
}

private fun readAttackCards(player: Player, exclude: Card? = null): List<Card> {
    while (true) {
        println("Attack with what value card?")

        val handMinusExcluded = when (exclude) {
            null -> player.hand
            else -> player.hand.filterOutFirst(exclude)
        }

        val cardValue = readInt()

        if (handMinusExcluded.any { it.value == cardValue }) {
            val numCardsInHand = handMinusExcluded.count { it.value == cardValue }
            if (numCardsInHand == 1)
                return handMinusExcluded.filter { it.value == cardValue }

            while (true) {
                println("How many cards would you like to use (0 to start over) (1..$numCardsInHand): ")
                val numCardsToUse = readInt()

                when (numCardsToUse) {
                    0 -> return emptyList()
                    in 1..numCardsInHand -> return handMinusExcluded.asSequence().filter { it.value == cardValue}.take(numCardsToUse).toList()
                    else -> println("Out of range")
                }
            }
        }
    }
}
