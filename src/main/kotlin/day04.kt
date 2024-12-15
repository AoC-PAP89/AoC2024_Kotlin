import util.readInput
import util.readTestInput
import java.nio.file.Paths

private fun main() {
    val day = 4
    val input = readInput(day)
    val testInput = readTestInput(day)

    /**
     * PART 1
     */
    part1(testInput).also {
        val testResult = 18L
        check(it == testResult)
    }
    part1(input)


    /**
     * PART 2
     */
    part2(testInput).also {
        val testResult = 9L
        check(it == testResult)
    }
    part2(input)
}

private fun part1(input: String): Long {
    val lines = input.lines().filter { it.isNotEmpty() }

    val xmas = "XMAS"
    val xmasBackwards = "SAMX"
    val listOfKeyWords = mutableListOf(xmas, xmasBackwards)

    var sum = 0

    sum += lines.findKeyWords(listOfKeyWords)

    val transposedLines = lines.transposeListOfStrings()
    sum += transposedLines.findKeyWords(listOfKeyWords)

    val diagonalLines = lines.extractDiagonal()
    sum += diagonalLines.findKeyWords(listOfKeyWords)

    val transposedDiagonalLines = lines.extractDiagonal2()
    sum += transposedDiagonalLines.findKeyWords(listOfKeyWords)

    println(sum)

    return sum.toLong()
}


private fun part2(input: String): Long {
    val lines = input.lines().filter { it.isNotEmpty() }

    val regEx1 = """(M.S.A.M.S)"""
    val regEx2 = """(S.M.A.S.M)"""
    val regEx3 = """(S.S.A.M.M)"""
    val regEx4 = """(M.M.A.S.S)"""

    val regEx = listOf(regEx1, regEx2, regEx3, regEx4)


    val numberOfLines = lines.count()
    val numberOfCharPerLine = lines[0].count()

    val sbs = mutableListOf<StringBuilder>().apply {
        for (i in ((numberOfCharPerLine - 2) * (numberOfLines - 2)) downTo 0) {
            add(StringBuilder())
        }
    }
    var foo = 0
    lines.windowed(3).forEachIndexed { linesWindowIndex, windowedLines ->
        windowedLines.forEachIndexed { lineWindowIndex, windowedLine ->
            val size2 = windowedLine.length - 2
            windowedLine.windowed(3).forEachIndexed { index, s ->
                sbs[((foo * size2)) + ((index))].append(s)
            }
        }
        foo++
    }

    val file = Paths.get("resources", "day04", "test_2.data").toFile()
    val newPuzzle = sbs.joinToString("\n") { it.toString() }
    file.writeText(newPuzzle)

    val sum = sbs.map { it.toString() }.findKeyWords(regEx)
    println(sum)
    return sum.toLong()
}


fun List<String>.extractDiagonal(): List<String> {
    val numberOfLines = this.count()
    val numberOfCharPerLine = this@extractDiagonal[0].count()

    val diagonal = mutableListOf<StringBuilder>().apply {
        for (i in (numberOfCharPerLine * 2 - 1) downTo 0) {
            add(StringBuilder())
        }
    }


    for (columnIndex in 0 until numberOfCharPerLine) {
        for (lineIndex in (numberOfLines - 1) downTo 0) {
            val diagonalIndex = numberOfLines - lineIndex + columnIndex - 1
            val char = this[lineIndex][columnIndex]
            diagonal[diagonalIndex].append(char)
        }
    }


    return diagonal.map { it.toString() }.toList().also {
        val saveFile = Paths.get("resources", "day04", "diagonal.data").toFile()
        saveFile.writeText(it.joinToString("\n"))
    }
}

fun List<String>.extractDiagonal2(): List<String> {
    val numberOfLines = this.count()
    val numberOfCharPerLine = this@extractDiagonal2[0].count()

    val diagonal = mutableListOf<StringBuilder>().apply {
        for (i in (numberOfCharPerLine * 2 - 1) downTo 0) {
            add(StringBuilder())
        }
    }


    for (columnIndex in 0 until numberOfCharPerLine) {
        for (lineIndex in 0 until numberOfLines) {
            val diagonalIndex = lineIndex + columnIndex
            val char = this[lineIndex][columnIndex]
            diagonal[diagonalIndex].append(char)
        }
    }


    return diagonal.map { it.toString() }.toList().also {
        val saveFile = Paths.get("resources", "day04", "diagonal2.data").toFile()
        saveFile.writeText(it.joinToString("\n"))
    }
}

private fun List<String>.findKeyWords(
    keyWordsRegExs: List<String>,
): Int {
    return sumOf { line ->
        keyWordsRegExs.map { it.toRegex() }.sumOf { regex ->
            regex.findAll(line).count()
        }
    }
}

fun List<String>.transposeListOfStrings(): List<String> {
    val transposed = mutableListOf<StringBuilder>().apply {
        for (i in this@transposeListOfStrings[0].count() downTo 0) {
            add(StringBuilder())
        }
    }

    for (line in this) {
        for ((charIndex, char) in line.toList().withIndex()) {
            transposed[charIndex].append(char)
        }
    }

    return transposed.map { it.toString() }.toList().also {
        val saveFile = Paths.get("resources", "day04", "transposed.data").toFile()
        saveFile.writeText(it.joinToString("\n"))
    }
}
