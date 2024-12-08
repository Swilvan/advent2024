import nl.swilvan.Equation
import nl.swilvan.parseInput
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day7Test {

    val input = listOf(
        "190: 10 19",
        "3267: 81 40 27",
        "83: 17 5",
        "156: 15 6",
        "7290: 6 8 6 15",
        "161011: 16 10 13",
        "192: 17 8 14",
        "21037: 9 7 18 13",
        "292: 11 6 16 20"
    )

    val equations = parseInput(input)

    @Test
    fun parse() {

        assertEquals(9, equations.size)

        assertEquals(Equation(190, listOf(10, 19)), equations[0])
        assertEquals(Equation(292, listOf(11, 6, 16, 20)), equations[8])
    }

    @Test
    fun canWorkEdgeCase() {
        assertTrue("292: 11 6 16 20 can be solved in exactly one way: 11 + 6 * 16 + 20") {
            Equation(292, listOf(11, 6, 16, 20)).canWork()
        }
    }

    @Test
    fun canWork() {
        val equationsByPossible = equations
            .groupBy { it.canWork() }

        assertEquals(6, equationsByPossible[true]!!.size)

    }


    /**
     *     156: 15 6 can be made true through a single concatenation: 15 || 6 = 156.
     *     7290: 6 8 6 15 can be made true using 6 * 8 || 6 * 15.
     *     192: 17 8 14 can be made true using 17 || 8 + 14.
     */
    @Test
    fun withConcat() {
        val concatInput = listOf(
                "156: 15 6",
                "7290: 6 8 6 15",
                "192: 17 8 14"
        )
        val concatQuations = parseInput(concatInput)

        val equationsByPossible = concatQuations
            .groupBy { it.canWork() }

        assertEquals(3, equationsByPossible[true]!!.size)
    }

}