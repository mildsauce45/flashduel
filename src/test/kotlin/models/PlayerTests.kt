package models

import org.junit.Test

class PlayerTests {
    @Test
    fun test_damaged_player_dies() {
        val player = testPlayer()
        assert(player.isAlive)

        player.takeHit()
        assert(!player.isAlive)
    }

    @Test
    fun test_recovering_playing_cannot_act() {
        val player = testPlayer()
        assert(player.canAct)

        player.isRecovering = true
        assert(!player.canAct)
    }

    private fun testPlayer(): Player {
        return Player("Test Player")
    }
}