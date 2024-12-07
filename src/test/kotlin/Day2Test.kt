import nl.swilvan.ascendingOrDescending
import nl.swilvan.diffNeverMoreThan3lessThan1
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day2Test {

    @Test
    fun ascendingOrDescending() {
        assertTrue("emptylist is ascending or descending") { ascendingOrDescending(listOf()) }
        assertTrue("single value list is ascending or descending") { ascendingOrDescending(listOf(1)) }
        assertTrue("two  value list is ascending or descending") { ascendingOrDescending(listOf(1, 2)) }
        assertFalse("four value list with higher middle value is not ascending or descending") {
            ascendingOrDescending(
                listOf(2, 3, 3, 1)
            )
        }


    }

    @Test
    fun repeatingNumbers() {
        assertFalse("repeating numbers are not ascending or descending") { ascendingOrDescending(listOf(1, 1, 1)) }
    }

    @Test
    fun textExamples() {


        assertTrue { ascendingOrDescending(listOf(7, 6, 4, 2, 1)) }   //: Safe without removing any level.
        assertTrue { ascendingOrDescending(listOf(1, 3, 2, 4, 5)) }  //: Safe by removing the second level, 3.
        assertTrue { ascendingOrDescending(listOf(8, 6, 4, 4, 1)) } //: Safe by removing the third level, 4.
        assertTrue { ascendingOrDescending(listOf(1, 3, 6, 7, 9)) } //: Safe without removing any level.

    }

    @Test
    fun testExamplesFails(){
        assertFalse { diffNeverMoreThan3lessThan1(listOf(9, 7, 6, 2, 1)) }//: Unsafe regardless of which level is removed.
        assertFalse { diffNeverMoreThan3lessThan1(listOf(1, 2, 7, 8, 9)) }//: Unsafe regardless of which level is removed.

    }

    @Test
    fun firstfails() {
        assertTrue("firstfails") { nl.swilvan.ascendingOrDescending(listOf(1, 3, 2, 1)) }
    }

    @Test
    fun lastFails() {
        assertTrue("lastFails") {
            ascendingOrDescending(listOf(1, 2, 3, 1))
        }
    }

    @Test
    fun secondFails() {
        assertTrue("secondFails") {
            ascendingOrDescending(listOf(1, 3, 2, 3))
        }
    }
}