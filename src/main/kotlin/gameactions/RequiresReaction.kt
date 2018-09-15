package gameactions

import models.Card

interface RequiresReaction {
    val attackCards: List<Card>
}
