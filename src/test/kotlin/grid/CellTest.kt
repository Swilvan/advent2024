package grid

import nl.swilvan.grid.Cell
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class CellTest {

    @Test
    fun test2dNeighbour() {
        val cell = Cell(0, 0, 0)
        val neighbours = listOf(
            Cell(-1, cell.x - 1, cell.y), //left
            Cell(-1, cell.x, cell.y + 1), //top
            Cell(-1, cell.x + 1, cell.y), //right
            Cell(-1, cell.x, cell.y + 1)  //bottom
        )

        neighbours.forEach{
            assertTrue("$it is a neighbour of $cell"){
                cell.is2dNeighbour(it)
            }
        }
    }
}