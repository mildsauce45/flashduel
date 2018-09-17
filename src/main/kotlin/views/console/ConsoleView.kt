package views.console

import engine.getPlayerIndex
import engine.getPlayerLocation
import models.CellType
import models.Game
import util.mapOf
import views.GameView

class ConsoleView(override val game: Game) : GameView {
    override fun display() {
        clearScreen()
        displayBoard()
    }

    private fun clearScreen() {
        println("\u001b[H\u001b[2J")
    }

    private fun displayBoard() {
        for (c in game.board.track) {
            when (c){
                CellType.W -> print("W ")
                CellType.B -> print("B ")
            }
        }
        println()

        val playerLocationMap = game.players.mapOf({ game.getPlayerLocation(it) }, { game.getPlayerIndex(it) + 1 })

        for (i in 0 until game.board.track.size) {
            print(when {
                playerLocationMap.containsKey(i) -> "${playerLocationMap[i]} "
                else -> "  "
            })
        }
        println()
    }

    override fun showMessage(msg: String) {
        println(msg)
    }
}