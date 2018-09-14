package engine

import models.Game

class GamePlayer(private val game: Game) {
    fun play() {
        game.start()

        while(!game.isGameOver) {
            val isPlayerRecovering = game.currentPlayer.isRecovering

            game.currentPlayer.strategy.startTurn(game)

            if (!isPlayerRecovering)
                takeTurn()

            game.currentPlayer.drawToFive(game)
            if (game.isTimeOver)
                doTimeOver()

            game.switchPlayer()
        }
    }

    private fun takeTurn() {
        val action = game.currentPlayer.strategy.getNextAction(game)

        val target = action.takeAction(game)

        action.player.discard(action.cardsToDiscard, game)

        if (target != null) {
            val reaction = target.strategy.getReaction(action, game)
            if (reaction != null) {
                reaction.take(game)
                reaction.player.discard(reaction.cardsToDiscard, game)
            }
        }
    }

    private fun doTimeOver() {
        // TODO: implement actual time up rules
        game.players[0].takeHit()
    }

}