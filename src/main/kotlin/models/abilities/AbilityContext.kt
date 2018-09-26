package models.abilities

import gameactions.GameAction
import gameactions.reactions.Reaction
import models.Game

data class AbilityContext(val game: Game, val action: GameAction? = null, val reaction: Reaction? = null)