package util

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URI
import java.nio.file.Paths
import javax.net.ssl.HttpsURLConnection


const val gitkeep = ".gitkeep"
const val inputFileName = "input.txt"
const val testFileName = "test.txt"
const val test2FileName = "test2.txt"
const val sessionCookieFile = "session"

val resourceDir: String = Paths.get("resources").toFile().absolutePath
val cookie = Paths.get(resourceDir, sessionCookieFile).toFile().readLines()[0].let {
    "session=$it"
}

fun readInput(day: Int): String {
    ensureFilesExist(day)
    val inputFile = Paths.get(resourceDir, "day${day.toString().padStart(2, '0')}", inputFileName).toFile()
    if (inputFile.readText().length < 10) {
        val url = "https://adventofcode.com/2024/day/$day/input"

        val connection = URI(url).toURL().openConnection() as HttpsURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Cookie", cookie)

        val responseCode = connection.responseCode
        val reader = if (responseCode in 200..299) {
            BufferedReader(InputStreamReader(connection.inputStream))
        } else {
            BufferedReader(InputStreamReader(connection.errorStream))
            error("could not read the puzzle input!")
        }

        val response = reader.use { it.readText() }

        inputFile.writeText(response)
    }
    return inputFile.readText().also {
        require(it.length > 10)
    }
}

fun ensureFilesExist(day: Int) {
    val dir = Paths.get(resourceDir, "day${day.toString().padStart(2, '0')}").toFile()
    if (!dir.exists()) {
        dir.mkdirs()
    }
    dir.resolve(gitkeep).apply {
        if (!exists()) {
            createNewFile()
        }
    }
    dir.resolve(inputFileName).apply {
        if (!exists()) {
            createNewFile()
        }
    }
    dir.resolve(testFileName).apply {
        if (!exists()) {
            createNewFile()
        }
    }
}

fun readTestInput(day: Int, isSecond: Boolean = false): String {
    val inputFile = Paths.get(
        resourceDir,
        "day${day.toString().padStart(2, '0')}",
        if (isSecond) {
            val dir = Paths.get(resourceDir, "day${day.toString().padStart(2, '0')}").toFile()
            dir.resolve(test2FileName).apply {
                if (!exists()) {
                    createNewFile()
                }
            }
            test2FileName
        } else testFileName
    ).toFile()
    return inputFile.readText().also {
        require(it.length > 10)
    }
}
