package grid

import nl.swilvan.grid.Cell
import nl.swilvan.grid.Coordinates
import nl.swilvan.grid.Grid
import nl.swilvan.grid.Grid.Companion.gridOfInt
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GridTest {
    val trivialInput = listOf(
        "0123",
        "1234",
        "8765",
        "9876"
    )

    @Test
    fun buildGrid() {
        val expected = Grid(
            listOf(
                listOf(0, 1, 2, 3),
                listOf(1, 2, 3, 4),
                listOf(8, 7, 6, 5),
                listOf(9, 8, 7, 6)
            )

        )
        val actual = gridOfInt(
            trivialInput
        )
        assertEquals(
            expected,
            actual
        )

        assertEquals(listOf(Coordinates(0, 0)), actual.findCellsWhere { it.value == 0 }.map { it.coordinates })
    }

    @Test
    fun neighborsFilled() {
        val grid = gridOfInt(trivialInput)

        val linksBoven = grid.getCell(0, 0)
        val rechtsOnder = grid.getCell(3, 3)

        assertEquals(listOf(1, 1), linksBoven.neighbours.map { it.value })
        assertEquals(listOf(7,5), rechtsOnder.neighbours.map { it.value })

        val midden = grid.getCell(1, 1)
        assertEquals(listOf(3,1,7,1), midden.neighbours.map { it.value })

        assertEquals(listOf(4,2,6,2), grid.getCell(2,1).neighbours.map { it.value })
    }
}