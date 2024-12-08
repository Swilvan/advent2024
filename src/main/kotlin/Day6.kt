package nl.swilvan

import java.io.File

fun main() {
    val input = File("src/main/resources/day6input").readLines().map { it.split("").filter { it.isNotEmpty() } }
    val guard = Guard(input)
    guard.walkUntil()
//    val vertices = createVertices(guard.map, guard.currentPosition)

    println(guard.visitedPositions.groupBy { it.p }.size)

//    println(input.mapIndexed{ y, line -> line.mapIndexed { x, c -> if(guard.loopOptions.contains(Position(x, y))) "0" else c  }})
    println(guard.loopOptions.size)
}


data class Vertex(val start: Position, val end: Position, val direction: Direction) {}

fun isInsideMap(position: Position, map: List<List<String>>) =
    position.x > -1 && position.x < map[0].size && position.y > -1 && position.y < map.size


class Guard(val map: List<List<String>>) {
    var direction: Direction
    var currentPosition: Position
    val visitedPositions: MutableSet<VisitedPosition>
    var loopOptions: MutableSet<Position>

    init {
        val startY = map.indexOfFirst { it.contains("^") }
        val startX = map[startY].indexOfFirst { it.contains("^") }
        currentPosition = Position(startX, startY)
        visitedPositions = mutableSetOf()
        loopOptions = mutableSetOf()
        direction = Direction.UP
    }

    fun walkUntil() {
        while (isInsideMap(currentPosition, map)) {
            if (direction.isBlocked(currentPosition, map)) direction = direction.changeDirection()
            else {
                if (direction.isInLine(currentPosition, visitedPositions)) {
                    loopOptions.add(direction.takeStep(currentPosition))
                }
                visitedPositions.add(VisitedPosition(currentPosition, direction))
                currentPosition = direction.takeStep(currentPosition)
            }
        }
    }

    private fun visitedPositionsContainsWithDirection(checkLoopPosition: Position, blockedDirection: Direction) =
        visitedPositions.find { it.p == checkLoopPosition && it.direction == blockedDirection } != null
}

data class VisitedPosition(val p: Position, val direction: Direction)

data class Position(val x: Int, val y: Int) {
    operator fun minus(other: Position): Position =
        Position(this.x - other.x, this.y - other.y)

    operator fun plus(other: Position): Position =
        Position(this.x + other.x, this.y + other.y)
}

enum class Direction {


    UP {
        override fun isBlocked(position: Position, map: List<List<String>>): Boolean =
            if (position.y - 1 < 0) false
            else map[position.y - 1][position.x] == "#"

        override fun takeStep(position: Position): Position =
            position.copy(y = position.y - 1)

        override fun changeDirection(): Direction = RIGHT

        override fun isInLine(current: Position, visitedPositions: Set<VisitedPosition>) =
            visitedPositions.firstOrNull { visp -> visp.p.y == current.y && visp.p.x > current.x && visp.direction == changeDirection() } != null

    },
    DOWN {
        override fun isBlocked(position: Position, map: List<List<String>>): Boolean =
            if (position.y + 1 >= map.size) false
            else map[position.y + 1][position.x] == "#"

        override fun takeStep(position: Position): Position =
            position.copy(y = position.y + 1)

        override fun changeDirection(): Direction = LEFT
        override fun isInLine(current: Position, visitedPositions: Set<VisitedPosition>) =
            visitedPositions.firstOrNull { visp -> visp.p.y == current.y && visp.p.x < current.x && visp.direction == changeDirection() } != null
    },
    LEFT {
        override fun isBlocked(position: Position, map: List<List<String>>): Boolean =
            if (position.x - 1 < 0) false
            else map[position.y][position.x - 1] == "#"

        override fun takeStep(position: Position): Position =
            position.copy(x = position.x - 1)

        override fun changeDirection(): Direction = UP
        override fun isInLine(current: Position, visitedPositions: Set<VisitedPosition>) =
            visitedPositions.firstOrNull { visp -> visp.p.y < current.y && visp.p.x == current.x && visp.direction == changeDirection() } != null
    },
    RIGHT {
        override fun isBlocked(position: Position, map: List<List<String>>): Boolean =
            if (position.x + 1 >= map[0].size) false
            else map[position.y][position.x + 1] == "#"

        override fun takeStep(position: Position): Position =
            position.copy(x = position.x + 1)

        override fun changeDirection(): Direction =
            DOWN

        override fun isInLine(current: Position, visitedPositions: Set<VisitedPosition>) =
            visitedPositions.firstOrNull { visp -> visp.p.y > current.y && visp.p.x == current.x && visp.direction == changeDirection() } != null
    };

    abstract fun isBlocked(position: Position, map: List<List<String>>): Boolean
    abstract fun takeStep(position: Position): Position
    abstract fun changeDirection(): Direction
    abstract fun isInLine(current: Position, visitedPositions: Set<VisitedPosition>): Boolean
}