package util

import models.Card
import org.junit.Assert.*
import org.junit.Test

class ExtensionsTests {
    @Test
    fun test_filter_out_first() {
        val testList = listOf(Card(1), Card(2), Card(2), Card(4), Card(5))

        val excludedCard = testList[1]

        assertEquals(2, excludedCard.value)

        val filteredList = testList.filterOutFirst(excludedCard)

        assertEquals(4, filteredList.size)
        assertEquals(1, filteredList.count { it == excludedCard })
    }

    @Test
    fun test_mapOf() {
        val testList = listOf("asfd", "foo", "qwerty")

        val map = testList.mapOf({ it.length }, {it[0]})

        assertEquals(3, map.size)
        assertEquals(setOf(4, 3, 6), map.keys)
        assertEquals(map[4], 'a')
        assertEquals(map[3], 'f')
        assertEquals(map[6], 'q')
    }
}