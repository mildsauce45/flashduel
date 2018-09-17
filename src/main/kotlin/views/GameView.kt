package views

import models.Game

interface GameView {
    val game: Game
    fun display()
    fun showMessage(msg: String)
}