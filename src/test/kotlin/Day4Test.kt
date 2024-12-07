import nl.swilvan.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day4Test {

    val testInputHorizontalVertical = listOf(
        listOf("X", "M", "A", "S", "X", "X"),
        listOf("S", "A", "M", "X", "S", "M"),
        listOf("S", "M", "M", "M", "A", "A"),
        listOf("S", "A", "A", "A", "M", "S"),
        listOf("S", "A", "M", "S", "X", "X"),
        listOf("S", "A", "S", "A", "M", "X"),
    )

    val testInputDiag = listOf(
        listOf("X", "X", "X", "X"),
        listOf("A", "M", "M", "M"),
        listOf("A", "A", "A", "A"),
        listOf("S", "S", "S", "S")
    )

    val testInputDiag2 = listOf(
        listOf("S", ".", ".", "S"),
        listOf(".", "A", "A", "."),
        listOf(".", "M", "M", "."),
        listOf("X", ".", ".", "X")
    )

    @Test
    fun horizontal() {
        assertEquals(1, matchHorizontal(testInputHorizontalVertical, 0, 0), "XMAS matches left to right")
        assertEquals(1, matchHorizontal(testInputHorizontalVertical, 0, 1), "SAMX matches right to left")
        assertEquals(1, matchHorizontal(testInputHorizontalVertical, 2, 5))

        assertEquals(7, countMatches(testInputHorizontalVertical))
        assertEquals(1, countMatches(listOf(listOf("S", "A", "M", "X"))))
    }

    @Test
    fun vertical() {
        assertEquals(
            1,
            matchVertical(testInputHorizontalVertical, 3, 1),
            "XMAS matches vertically"
        )

        assertEquals(
            1,
            matchVertical(testInputHorizontalVertical, 5, 0),
            "XMAS matches vertically"
        )

        assertEquals(
            1,
            matchVertical(testInputHorizontalVertical, 4, 1),
            "SAMX matches vertically"
        )
    }

    @Test
    fun diagonal() {
//        assertEquals(1, matchDiagonal(testInputDiag, 0, 0), "XMAS matches top left bottom right")
//        assertEquals(1, matchDiagonal(testInputDiag, 0, 3), "SAMX matches bottom left to top right")
//        assertEquals(1, matchDiagonal(testInputDiag2, 0, 0), "SAMX matches top left bottom right")
        assertEquals(1, matchDiagonal(testInputDiag2, 0, 3, "X", "S", "MAS", "AMX"), "XMAS matches bottom left to top right")
        assertEquals(5, countMatches(testInputDiag))
    }

    @Test
    fun sumTest() {
        val sumInput = listOf(
            listOf("M", "M", "M", "S", "X", "X", "M", "A", "S", "M"),
            listOf("M", "S", "A", "M", "X", "M", "S", "M", "S", "A"),
            listOf("A", "M", "X", "S", "X", "M", "A", "A", "M", "M"),
            listOf("M", "S", "A", "M", "A", "S", "M", "S", "M", "X"),
            listOf("X", "M", "A", "S", "A", "M", "X", "A", "M", "M"),
            listOf("X", "X", "A", "M", "M", "X", "X", "A", "M", "A"),
            listOf("S", "M", "S", "M", "S", "A", "S", "X", "S", "S"),
            listOf("S", "A", "X", "A", "M", "A", "S", "A", "A", "A"),
            listOf("M", "A", "M", "M", "M", "X", "M", "M", "M", "M"),
            listOf("M", "X", "M", "X", "A", "X", "M", "A", "S", "X")
        )
        assertEquals(18, countMatches(sumInput))
    }

    @Test
    fun `x-mas`() {
       val xmasInput = listOf(
            listOf(".", "M", ".", "S", ".", ".", ".", ".", ".", "."),
            listOf(".", ".", "A", ".", ".", "M", "S", "M", "S", "."),
            listOf(".", "M", ".", "S", ".", "M", "A", "A", ".", "."),
            listOf(".", ".", "A", ".", "A", "S", "M", "S", "M", "."),
            listOf(".", "M", ".", "S", ".", "M", ".", ".", ".", "."),
            listOf(".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
            listOf("S", ".", "S", ".", "S", ".", "S", ".", "S", "."),
            listOf(".", "A", ".", "A", ".", "A", ".", "A", ".", "."),
            listOf("M", ".", "M", ".", "M", ".", "M", ".", "M", "."),
            listOf(".", ".", ".", ".", ".", ".", ".", ".", ".", ".")
        )

        assertEquals(9, `countX-Mas`(xmasInput))
    }


}