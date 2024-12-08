import nl.swilvan.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day8Test {

    val basicInput = listOf(
        "..........",
        "...#......",
        "..........",
        "....a.....",
        "..........",
        ".....a....",
        "..........",
        "......#...",
        "..........",
        ".........."
    )

    val basicMap = parseInputDay8(basicInput)
    val basicAntennas = basicMap.antennas

    @Test
    fun parseInput() {
        assertEquals(basicAntennas.size, 2)
        assertEquals(Antenna('a', Position(4, 3)), basicAntennas[0])
        assertEquals(Antenna('a', Position(5, 5)), basicAntennas[1])
    }

    @Test
    fun positionAndAntennaMinus() {
        assertEquals(Position(-1, -2), Position(4, 3) - Position(5, 5))
        assertEquals(Antenna('a', Position(-1, -2)), Antenna('a', Position(4, 3)) - Antenna('b', Position(5, 5)))
    }

    @Test
    fun findAntinodes() {
        assertEquals(
            setOf(
                Position(3, 1),
                Position(6, 7),
                Position(4,3),
                Position(5,5),
                Position(7,9)
            ), basicMap.findAntinodes(basicAntennas[0], basicAntennas[1])
        )
    }

    val lessBasicAntennas = parseInputDay8(
        listOf(
            "..........",
            "...#......",
            "#.........",
            "....a.....",
            "........a.",
            ".....a....",
            "..#.......",
            "......A...",
            "..........",
            ".........."
        )
    )

    @Test
    fun antennasOnlyOnMap() {
        assertEquals(Position(10, 10), lessBasicAntennas.size)

        assertEquals(
            setOf(
                Position(4, 3),
                Position(0, 2),
                Position(8,4),
                Position(3, 1),
                Position(2, 6),
                Position(6, 7),
                Position(5, 5),
                Position(7, 9),
                Position(2,6)
            ),
            lessBasicAntennas.findAntinodes()
        )
    }

    @Test
    fun testFindUniquePairs() {
        assertEquals(
            listOf(
                1 to 2,
                1 to 3,
                1 to 4,
                2 to 3,
                2 to 4,
                3 to 4
            ),
            uniquePairsOrderIndependent(listOf(1, 2, 3, 4))
        )
    }

    @Test
    fun countAntinodes() {
        val antennaMap = parseInputDay8(
            listOf(
                "......#....#",
                "...#....0...",
                "....#0....#.",
                "..#....0....",
                "....0....#..",
                ".#....A.....",
                "...#........",
                "#......#....",
                "........A...",
                ".........A..",
                "..........#.",
                "..........#."
            )
        )
        val antinodes = antennaMap.findAntinodes()
        assertEquals(34, antinodes.size)
    }


    @Test
    fun PositionTest() {
        assertEquals(Position(1, 2), Position(3, 3) - Position(2, 1))
        assertEquals(Position(5, 4), Position(3, 3) + Position(2, 1))

        assertTrue("x less or y less means position smaller") {
            Position(-1, 0) < Position(0, 0)
        }
        assertTrue("x less or y less means position smaller") {
            Position(0, -1) < Position(0, 0)
        }

        assertTrue("x more or y more means position larger") {
            Position(1, 0) > Position(0, 0)
        }
        assertTrue("x more or y more means position larger") {
            Position(0, 1) > Position(0, 0)
        }

        assertEquals(Position(1, -1), -Position(-1, 1), "unaryMinus flips both signs")
    }
}