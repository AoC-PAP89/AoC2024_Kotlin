import util.readInput
import util.readTestInput

val dont = """don't()"""
val doo = """do()"""

private fun main() {
    val day = 3
    val input = readInput(day).lines().joinToString("").trim()
    val testInput = readTestInput(day).lines().joinToString("").trim()
    val testInput2 = readTestInput(day, true).lines().joinToString("").trim()

    /**
     * PART 1
     */
    part1(testInput).also {
        val testResult = 161L
        require(it == testResult)
    }
    part1(input)


    /**
     * PART 2
     */
    part2(testInput2).also {
        val testResult = 48L
        require(it == testResult)
    }
    part2(input)
}

private fun part1(input: String): Long {
    return multiply(input)
}


private fun part2(input: String): Long {
    val sb = StringBuilder()
    var first = true

    input.split(dont).onEach { afterSplit ->
        if (first) {
            sb.append(afterSplit)
            first = false
            return@onEach
        }
        println(afterSplit)
        sb.append(afterSplit.substringAfter(doo).let { sanitized ->
            println(sanitized)
            if (sanitized == afterSplit)
                ""
            else
                sanitized
        })
    }

    val sanitizedTxt = sb.toString()
    println(sanitizedTxt)
    return multiply(sanitizedTxt)
}

private fun multiply(txt: String): Long {
    val rgex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
    val matches = rgex.findAll(txt)
    val sum = matches.sumOf {
        it.groups[1]!!.value.toLong() * it.groups[2]!!.value.toLong()
    }
    println(sum)
    return sum
}
