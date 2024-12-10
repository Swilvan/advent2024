import nl.swilvan.grid.Coordinates
import nl.swilvan.grid.Grid
import nl.swilvan.grid.Grid.Companion.gridOfInt
import nl.swilvan.trailEnds
import nl.swilvan.trailStartRating
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day10Test {

    val trivialInput = listOf(
        "0123",
        "1234",
        "8765",
        "9876"
    )
    val basicInput = listOf(
        "89010123",
        "78121874",
        "87430965",
        "96549874",
        "45678903",
        "32019012",
        "01329801",
        "10456732",
    )

    @Test
    fun hikingTrails() {
        val grid = gridOfInt(trivialInput)

        assertEquals(Coordinates(0, 3), trailEnds(Coordinates(0, 0), grid).first().coordinates)

        val basicGrid = gridOfInt(basicInput)
        assertEquals(5, trailEnds(Coordinates(2, 0), basicGrid).toSet().size)

        assertEquals(36, basicGrid.findCellsWhere { it.value == 0 }.sumOf { trailEnds(it).toSet().size })
    }

    @Test
    fun ratings() {
        val grid = gridOfInt(basicInput)

        assertEquals(20, trailStartRating(Coordinates(2, 0), grid))
    }
}