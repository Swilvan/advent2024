import nl.swilvan.Stone
import nl.swilvan.splitNumbers
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {

    @Test
    fun stone0() {
        val s = Stone()

        assertEquals(listOf(Stone(1)), s.blink())
    }

    @Test
    fun stone1() {
        (1..9L).forEach {
            assertEquals(listOf(it * 2024), Stone(it).blink().map { it.value })
        }
    }

    @Test
    fun stoneEvenNumberDigits() {
        (10..19L).forEach {
            assertEquals(
                "$it".split("").filter { it.isNotBlank() }.map { it.toLong() }.toList(),
                Stone(it).blink().map { it.value })
        }
    }

    @Test
    fun progression() {
        val initialNumbers = listOf(125L, 17)
        var stones = initialNumbers.map { Stone(it) }.flatMap { it.blink() }
        var compressed = initialNumbers.map { it to 1L }.toMap().toMutableMap()

        assertEquals(listOf(253000L, 1, 7), stones.map { it.value })
        splitNumbers(compressed)
        assertEquals(
            mapOf(253000L to 1L, 1L to 1L, 7L to 1L).toMutableMap(),
            compressed.filter { it.value != 0L }.toMutableMap()
        )

        stones = stones.flatMap { it.blink() }

        assertEquals(listOf(253L, 0, 2024, 14168), stones.map { it.value })
        splitNumbers(compressed)
        assertEquals(
            mapOf(253L to 1L, 0L to 1L, 2024L to 1L, 14168L to 1).toMutableMap(),
            compressed.filter { it.value != 0L }.toMutableMap()
        )

        stones = stones.flatMap { it.blink() }

        assertEquals(
            listOf(512072L, 1, 20, 24, 28676032),
            stones.map { it.value })
        splitNumbers(compressed)
        assertEquals(
            mapOf(512072L to 1L, 1L to 1L, 20L to 1, 24L to 1L, 28676032L to 1).toMutableMap(),
            compressed.filter { it.value != 0L }.toMutableMap()
        )

        stones = stones.flatMap { it.blink() }

        assertEquals(
            listOf(512L, 72, 2024, 2, 0, 2, 4, 2867, 6032),
            stones.map { it.value })
        splitNumbers(compressed)
        assertEquals(
            mapOf(
                512L to 1,
                72L to 1L,
                2024L to 1L,
                2L to 2,
                0L to 1,
                4L to 1L,
                2867L to 1,
                6032L to 1
            ).toMutableMap(), compressed.filter { it.value != 0L }.toMutableMap()
        )

        stones = stones.flatMap { it.blink() }

        assertEquals(
            listOf(1036288L, 7, 2, 20, 24, 4048, 1, 4048, 8096, 28, 67, 60, 32),
            stones.map { it.value })
        splitNumbers(compressed)
        assertEquals(
            mapOf(
                1036288L to 1,
                7L to 1,
                2L to 1L,
                20L to 1,
                24L to 1L,
                4048L to 2,
                1L to 1,
                8096L to 1L,
                28L to 1,
                67L to 1,
                60L to 1,
                32L to 1
            ).toMutableMap().toList().sortedBy { it.first }, compressed.filter { it.value != 0L }.toMutableMap().toList().sortedBy { it.first }
        )

        stones = stones.flatMap { it.blink() }

        assertEquals(
            listOf(2097446912L, 14168, 4048, 2, 0, 2, 4, 40, 48, 2024, 40, 48, 80, 96, 2, 8, 6, 7, 6, 0, 3, 2),
            stones.map { it.value })
        splitNumbers(compressed)
        assertEquals(
            mapOf(
                2097446912L to 1L,
                14168L to 1L,
                4048L to 1L,
                2L to 4L,
                0L to 2L,
                4L to 1L,
                40L to 2,
                48L to 2L,
                2024L to 1,
                80L to 1,
                96L to 1,
                8L to 1,
                6L to 2,
                7L to 1,
                3L to 1
            ).toMutableMap().toList().sortedBy { it.first }, compressed.filter { it.value != 0L }.toMutableMap().toList().sortedBy { it.first }
        )
    }
}