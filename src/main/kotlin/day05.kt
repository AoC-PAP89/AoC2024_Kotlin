import util.readInput
import util.readTestInput

private fun main() {
    val day = 5
    val input = readInput(day)
    val testInput = readTestInput(day)

    /**
     * PART 1
     */
    part1(testInput).also {
        val testResult = 143L
        println("Part1: expected $testResult, found $it")
        require(it == testResult)
    }
    part1(input).also {
        println("result part1: $it")
    }


    /**
     * PART 2
     */
    part2(testInput).also {
        val testResult = 123L
        println("Part2: expected $testResult, found $it")
        require(it == testResult)
    }
    part2(input).also {
        println("result part2: $it")
    }
}

private fun part1(input: String): Long {
    val (rulesTxt, updatesTxt) = input.split("\r\n\r\n").let {
        it[0] to it[1]
    }
    val rules = parseRules(rulesTxt)
    val updates = parseUpdates(updatesTxt)

    var sum = 0L
    updates.forEach updates@{ update ->
        rules.forEach rules@{ (firstPage, pagesAfter) ->
            val firstPos = update.indexOf(firstPage)
            if (firstPos != -1) {
                val afterPosMin = pagesAfter.minOf { page ->
                    update.indexOf(page).let { index ->
                        if (index == -1)
                            update.size
                        else
                            index
                    }
                }
                if (afterPosMin < firstPos) {
                    return@updates
                }
            }
        }
        sum += update[update.size / 2]
    }

    return sum
}

fun parseUpdates(updatesTxt: String) = updatesTxt.lines()
    .filterNot { it.isEmpty() }
    .map { line ->
        line.split(",").map { it.toInt() }
    }.toList()


private fun parseRules(rulesTxt: String): Map<Int, MutableList<Int>> {
    val rules = mutableMapOf<Int, MutableList<Int>>()
    rulesTxt.lines().map { line ->
        line.split("|").map { it.toInt() }.also {
            rules.getOrPut(it[0]) {
                mutableListOf()
            }.add(it[1])
        }
    }
    return rules.toMap()
}


private fun part2(input: String): Long {


    return 1
}
