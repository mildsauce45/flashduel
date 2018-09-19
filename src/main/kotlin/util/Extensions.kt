package util

import java.util.*

fun <K,V,T> Iterable<T>.mapOf(keyTransformer: (T) -> K, valueTransformer: (T) -> V): Map<K, V> {
    val retMap = HashMap<K, V>()

    for (t in this)
        retMap[keyTransformer(t)] = valueTransformer(t)

    return retMap
}

// Removes the first instance T from the list
fun <T> List<T>.filterOutFirst(toExclude: T): List<T> {
    val result = Vector<T>()

    var skipped = false
    for (t in this) {
        if (t != toExclude || !skipped)
            result.add(t)

        if (t == toExclude)
            skipped = true
    }

    return result
}