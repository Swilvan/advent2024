import nl.swilvan.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day9Test {

    val extendedInput = "2333133121414131402"


    @Test
    fun input() {
//        "00...111...2L...333.44.5555.6666.777.888899"
        assertEquals(
            listOf(
                0L to 2L,
                -1L to 3L,
                1L to 3L,
                -1L to 3L,
                2L to 1L,
                -1L to 3L,
                3L to 3L,
                -1L to 1L,
                4L to 2L,
                -1L to 1L,
                5L to 4L,
                -1L to 1L,
                6L to 4L,
                -1L to 1L,
                7L to 3L,
                -1L to 1L,
                8L to 4L,
                9L to 2L
            ).toFileEncoding(), parseDay9(extendedInput)
        )
    }

    @Test
    fun trivial() {
        val baseCaseInput = "234"
        assertEquals(listOf(
            0L to 2L,
            -1L to 3L,
            1L to 4L
        ).toFileEncoding(), parseDay9(baseCaseInput))

        assertEquals(listOf(
            0L to 2L,
            1L to 3L,
            1L to 1L
        ).toFileEncoding(), defragment(parseDay9(baseCaseInput)))
    }

    @Test
    fun extendedInput(){
        val fileEncodings = listOf(
            0L to 2L,
            9L to 2L,
            8L to 1L,
            1L to 3L,
            8L to 3L,
            2L to 1L,
            7L to 3L,
            3L to 3L,
            6L to 1L,
            4L to 2L,
            6L to 1L,
            5L to 4L,
            6L to 1L,
            6L to 1L
        ).toFileEncoding()
        assertEquals(
            fileEncodings, defragment(parseDay9(extendedInput))
        )

        assertEquals(1928, fileEncodings.checkSum().result )
    }

    @Test
    fun recurseFullFiles(){
        val fragments = recurseFullFiles(fragments = parseDay9(extendedInput))
//        println(fragments.map { "${it.id}: ${it.size} " }.joinToString("") )
        println(fragments.map{enc -> List(enc.size.toInt()){ if(enc.id <0 ) "." else "${enc.id}"}.joinToString("")})
//        assertEquals(2858, fragments.checkSum().result )
    }

}