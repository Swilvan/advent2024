import nl.swilvan.findAreaOf
import nl.swilvan.grid.Cell
import nl.swilvan.grid.Coordinates
import nl.swilvan.grid.Grid.Companion.gridOf
import nl.swilvan.perimeter
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day12Test {

    val trivialExample = listOf(
        "AAA",
        "BBB",
        "AAA"
    )

    val basiceExample = listOf(
        "RRRRIICCFF",
        "RRRRIICCCF",
        "VVRRRCCFFF",
        "VVRCCCJFFF",
        "VVVVCJJCFE",
        "VVIVCCJJEE",
        "VVIIICJJEE",
        "MIIIIIJJEE",
        "MIIISIJEEE",
        "MMMISSJEEE",
    )

    @Test
    fun findAreaOfTest() {
        val grid = gridOf(trivialExample) { it }

        assertEquals(setOf((0..2).map { Cell("B", it, 1) }.toSet()), grid.findAreaOf("B"))

        val areaA = setOf(
            (0..2).map { Cell("A", it, 0) }.toSet(),
            (0..2).map { Cell("A", it, 2) }.toSet()
        )

        assertEquals(areaA, grid.findAreaOf("A"))
    }

    @Test
    fun trivialPerimeter() {
        val grid = gridOf(trivialExample) { it }

        assertEquals(4, Cell("X", 0, 0).perimeter())
        val cellSurroundedBySameValueNeighbours = Cell(
            "X", Coordinates(-1, -1),
            (0..3).map {
                Cell("X", it, it)
            }.toSet()
        )
        assertEquals(0, cellSurroundedBySameValueNeighbours.perimeter())

        assertEquals(3, grid.getCell(0, 0).perimeter())
        assertEquals(2, grid.getCell(1, 1).perimeter())
    }

    @Test
    fun findAreaBasicExample() {
        val grid = gridOf(basiceExample) { it }

        val areas = grid.uniqueValues.flatMap {
            grid.findAreaOf(it)
        }

        assertEquals(
            mapOf(
                "R" to 12, // * 18
                "I" to 4, // * 8
                "C" to 14, // * 28
                "F" to 10, // * 18
                "V" to 13, // * 20
                "J" to 11, // * 20
                "C" to 1, // * 4
                "E" to 13, // * 18
                "I" to 14, // * 22
                "M" to 5, // * 12
                "S" to 3, // * 8
            ), areas.associate { it.first().value to it.size }
        )
        assertEquals(
            mapOf(
                "R" to "12 * 18",
                "I" to "4 * 8",
                "C" to "14 * 28",
                "F" to "10 * 18",
                "V" to "13 * 20",
                "J" to "11 * 20",
                "C" to "1 * 4",
                "E" to "13 * 18",
                "I" to "14 * 22",
                "M" to "5 * 12",
                "S" to "3 * 8",
            ), areas.associate { it.first().value to "${it.size} * ${it.sumOf { it.perimeter() }}"}
        )

        assertEquals(1930, areas.sumOf { a -> a.size * a.sumOf { it.perimeter() } })
    }
}
