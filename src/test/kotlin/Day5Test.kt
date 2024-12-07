import nl.swilvan.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day5Test {
    val testInput = listOf(
        "47|53",
        "97|13",
        "97|61",
        "97|47",
        "75|29",
        "61|13",
        "75|53",
        "29|13",
        "97|29",
        "53|29",
        "61|53",
        "97|53",
        "61|29",
        "47|13",
        "75|47",
        "97|75",
        "47|61",
        "75|61",
        "47|29",
        "75|13",
        "53|13",

        "75,47,61,53,29",
        "97,61,53,29,13",
        "75,29,13",
        "75,97,47,61,53",
        "61,13,29",
        "97,13,75,29,47",
    )

    @Test
    fun getPageOrderRegister() {
        val pageOrder = pageOrderRegister(testInput)
        assertEquals(6, pageOrder.size)
        assertEquals(listOf(53, 13, 61, 29), pageOrder[47])
    }

    @Test
    fun pageOrderings() {
        val pageOrder = pageOrderRegister(testInput)
        val pageOrdering = pageOrderings(testInput)
        println(pageOrdering)

        val correctOrderings = listOf(0, 1, 2)

        pageOrdering.forEachIndexed { idx: Int, item: List<Int> ->
            if (correctOrderings.contains(idx)) {
                assertTrue("ordering: $item is correct") {
                    correctOrdering(pageOrder, item)
                }
            } else {
                assertFalse("ordering: $item not correct") {
                    correctOrdering(pageOrder, item)
                }
            }
        }
    }

    @Test
    fun sumCorrectOrderings(){
        assertEquals(143, getSumOfMiddleOfCorrectOrderings(pageOrderings(testInput), pageOrderRegister(testInput)))
    }

    @Test
    fun reOrder(){
        val pageOrder = pageOrderRegister(testInput)
        val pageOrdering = pageOrderings(testInput)

        val incorrectOrderings = listOf(
            listOf(75,97,47,61,53),
            listOf(61,13,29),
            listOf(97,13,75,29,47)
        )

        assertEquals(incorrectOrderings, pageOrdering.filter { !correctOrdering(pageOrder, it) })

        val reordered = listOf(
            listOf(97,75,47,61,53),
            listOf(61,29,13),
            listOf(97,75,47,29,13)
        )

        assertEquals(reordered, reorder(incorrectOrderings, pageOrder)) }

}

