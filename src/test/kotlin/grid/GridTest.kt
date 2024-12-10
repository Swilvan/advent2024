package grid

import nl.swilvan.grid.Cell
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

        assertEquals(listOf(Cell(0, 0, 0)), actual.findCellsWhere { it == 0 })
    }
}