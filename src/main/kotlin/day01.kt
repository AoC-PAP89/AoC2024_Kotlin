import java.nio.file.Paths
import kotlin.math.abs

private fun main() {
    part1()
    part2()
}

private fun part1() {
    val (first, second) = getLists()
    var sum = 0
    for ((index, num) in first.withIndex()) {
        sum += abs(num - second[index])
    }
    println(sum)
}

private fun part2() {
    val (first, second) = getLists()
    val counts = second.groupingBy { it }.eachCount()
    val sum = first.sumOf {
        it * (counts[it] ?: 0)
    }
    println(sum)
}

private fun getLists(): Pair<List<Int>, List<Int>> {
    val data = Paths.get("resources", "day01", "input.txt").toFile().readLines()
    return data
        .map { it.split("   ") }
        .map { it[0].toInt() to it[1].toInt() }
        .unzip()
        .let { (first, second) ->
            first.sorted() to second.sorted()
        }
}
