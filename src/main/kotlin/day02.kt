import util.readInput
import util.readTestInput
import kotlin.math.abs

private fun main() {
    val day = 2
    val input = readInput(day)
    val testInput = readTestInput(day)

    /**
     * PART 1
     */
    part1(testInput).also {
        val testResult = 2
        require(it == testResult)
    }
    part1(input)


    /**
     * PART 2
     */
    part2(testInput).also {
        val testResult = 4
        require(it == testResult)
    }
    part2(input)
}

private fun part1(input: String): Int {
    val lines = input.lines().dropLast(1).map { line ->
        line.split(" ").map { intStr -> intStr.toInt() }
    }
    var safes = 0
    lines.forEach {
        var safe = if (it.isAscOrDesc()) {
            it.isDistanceSafe()
        } else {
            false
        }
        if (safe) {
            safes++
        }
    }
    println("safes: $safes")

    return safes
}


private fun part2(input: String): Int {
    val lines = input.lines().dropLast(1).map { line ->
        line.split(" ").map { intStr -> intStr.toInt() }
    }
    var safes = 0
    lines.forEach each@{ line ->
        if (line.isAscOrDesc()) {
            if (line.isDistanceSafe()) {
                safes++
                return@each
            }
        }

        if (line.isDistanceSafe2()) {
            safes++
        }
    }
    println("safes: $safes")

    return safes
}

private fun List<Int>.isDistanceSafe(): Boolean {
    windowed(2).forEach {
        val distance = abs(it[0] - it[1])
        if (distance < 1 || distance > 3) {
            return false
        }
    }
    return true
}

private fun List<Int>.isDistanceSafe2(): Boolean {
    this.forEachIndexed { idx, _ ->
        val drop = this.toMutableList().apply {
            removeAt(idx)
        }.toList()
        if (drop.isAscOrDesc() && drop.isDistanceSafe()) {
            return true
        }
    }
    return false
}

private fun List<Int>.isAscOrDesc(): Boolean {
    val asc = sorted()
    val desc = sortedDescending()
    return ((this == asc) || (this == desc))
}
