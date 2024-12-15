import util.readInput
import util.readTestInput

private fun main() {
    val day = 6
    val input = readInput(day)
    val testInput = readTestInput(day)

    /**
     * PART 1
     */
    part1(testInput).also {
        val testResult = 41L
        require(it == testResult)
    }
    part1(input).also {
        println("result part1: $it")
    }


    /**
     * PART 2
     */
    part2(testInput).also {
        val testResult = 6L
        require(it == testResult)
    }
    part2(input).also {
        println("result part2: $it")
    }
}

private fun part1(input: String): Long {
    val map = GameMap.from(input.lines())
    val game = Game(
        map,
        Player(map.startPosition, Direction.NORTH)
    )

    var moves = 0
    while (game.nextMove()) {
        moves++
        if (moves % 10 == 0) {
            println(moves)
        }
    }
    val visitedTiles = game.gameMap.tiles.values.count { it == MapObject.VISITED }
    return visitedTiles.toLong()
}


private fun part2(input: String): Long {


    return 1
}

private data class Game(
    val gameMap: GameMap,
    val guard: Player,
) {
    fun nextMove(): Boolean {
        val currentPosition = guard.position
        var direction = guard.direction
        val nextPosition = currentPosition.let {
            var next = it.next(direction)
            while (
                gameMap.tiles[next] == MapObject.SOME
            ) {
                direction = direction.next()
                next = it.next(direction)
            }
            next
        }
        guard.apply {
            position = nextPosition
            this.direction = direction
        }
        gameMap.tiles[currentPosition] = MapObject.VISITED
        return nextPosition in gameMap.tiles
    }
}

private data class GameMap(
    val tiles: MutableMap<Coordinates, MapObject>,
    val startPosition: Coordinates,
) {
    companion object {
        fun from(lines: List<String>): GameMap {
            var guardPosition = Coordinates(0, 0)
            val map = mutableMapOf<Coordinates, MapObject>()
            lines.forEachIndexed { rowIndex, line ->
                line.forEachIndexed { columnIndex, c ->
                    val currentCoordinates = Coordinates(rowIndex + 1, columnIndex + 1)
                    map[currentCoordinates] = MapObject.from(c).also {
                        if (it == MapObject.START) {
                            guardPosition = currentCoordinates
                        }
                    }
                }
            }
            return GameMap(map, guardPosition)
        }
    }
}


private data class Player(
    var position: Coordinates,
    var direction: Direction,
)


private data class Coordinates(
    val row: Int,
    val column: Int,
) {
    fun next(direction: Direction): Coordinates {
        return when (direction) {
            Direction.NORTH -> Coordinates(row - 1, column)
            Direction.EAST -> Coordinates(row, column + 1)
            Direction.SOUTH -> Coordinates(row + 1, column)
            Direction.WEST -> Coordinates(row, column - 1)
        }
    }
}

private enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    fun next() = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }
}

private enum class MapObject(char: Char) {
    UNKNOWN(' '),
    START('^'),
    NONE('.'),
    SOME('#'),
    VISITED('X');

    companion object {
        fun from(c: Char): MapObject {
            return when (c) {
                '#' -> SOME
                '.' -> NONE
                '^' -> START
                'X' -> VISITED
                else -> UNKNOWN
            }
        }
    }
}
