package engine

enum class Direction {
    LEFT,
    RIGHT
}

fun Direction.getMultiplier(): Int {
    return when (this) { Direction.LEFT -> -1
        else -> 1
    }
}