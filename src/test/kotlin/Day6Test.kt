import nl.swilvan.Direction
import nl.swilvan.Guard
import nl.swilvan.Position
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day6Test {

    val testStart = listOf(
        "....#.....",
        ".........#",
        "..........",
        "..#.......",
        ".......#..",
        "..........",
        ".#..^.....",
        "........#.",
        "#.........",
        "......#..."
    ).map { it.split("").filter(String::isNotBlank) }

    val guard = Guard(testStart)

    @Test
    fun createLoop(){
        val start = guard.currentPosition
        guard.walkUntil()
        println(guard.loopOptions)

        testStart.mapIndexed{ y, line -> line.mapIndexed { x, c -> if(guard.loopOptions.contains(Position(x, y))) "0" else if(start == Position(x,y)) "^" else c  }}.forEach(::println)
        assertEquals(6, guard.loopOptions.size)
    }

    @Test
    fun walk(){
        guard.walkUntil()
        assertEquals(41, guard.visitedPositions.size)
    }

    @Test
    fun start() {

        assertEquals(Position(4, 6), guard.currentPosition)
        assertEquals(setOf(), guard.visitedPositions)
        assertEquals(Direction.UP, guard.direction)
        assertFalse("can step up from start") {
            guard.direction.isBlocked(guard.currentPosition, testStart)
        }
    }

    @Test
    fun directionUp() {
        val testUpMap = listOf(
            listOf("#", "."),
            listOf(".", ".")
        )

        val direction = Direction.UP

        assertTrue("# means blocked") {
            direction.isBlocked(Position(0, 1), testUpMap)
        }

        assertFalse("end of map is not blocked") {
            direction.isBlocked(Position(1, 0), testUpMap)
        }

        assertEquals(Position(1, 0), direction.takeStep(Position(1, 1)))

        assertEquals(Direction.RIGHT, direction.changeDirection())
    }

    @Test
    fun directionRight() {
        val testUpMap = listOf(
            listOf(".", "#"),
            listOf(".", ".")
        )

        val direction = Direction.RIGHT
        assertTrue("# means blocked") {
            direction.isBlocked(Position(0, 0), testUpMap)
        }

        assertFalse("end of map is not blocked") {
            direction.isBlocked(Position(1, 1), testUpMap)
        }

        assertEquals(Position(1, 1), direction.takeStep(Position(0, 1)))

        assertEquals(Direction.DOWN, direction.changeDirection())
    }

    @Test
    fun directionDown() {
        val testUpMap = listOf(
            listOf(".", "."),
            listOf(".", "#")
        )

        val direction = Direction.DOWN
        assertTrue("# means blocked") {
            direction.isBlocked(Position(1, 0), testUpMap)
        }

        assertFalse("end of map is not blocked") {
            direction.isBlocked(Position(0, 1), testUpMap)
        }

        assertEquals(Position(0, 1), direction.takeStep(Position(0, 0)))

        assertEquals(Direction.LEFT, direction.changeDirection())
    }

    @Test
    fun directionLeft() {
        val testUpMap = listOf(
            listOf(".", "."),
            listOf("#", ".")
        )

        val direction = Direction.LEFT
        assertTrue("# means blocked") {
            direction.isBlocked(Position(1, 1), testUpMap)
        }

        assertFalse("end of map is not blocked") {
            direction.isBlocked(Position(0, 0), testUpMap)
        }

        assertEquals(Position(0, 0), direction.takeStep(Position(1, 0)))

        assertEquals(Direction.UP, direction.changeDirection())
    }
}