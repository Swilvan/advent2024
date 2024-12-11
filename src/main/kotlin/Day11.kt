package nl.swilvan

import java.io.File
import java.math.BigInteger

fun main() {
    val splitInput = File("src/main/resources/day11input").readLines()[0].split(" ").filter { it.isNotBlank() }
//    val stones = splitInput.map { Stone(it.toLong()) }
    val occurences = mutableMapOf<Long, Long>()
    splitInput.map { it.toLong() }.forEach {
        occurences.merge(it, 1, Long::plus)
    }

    (0..74).forEach {
        splitNumbers(occurences)
        if (it == 24)
            println(occurences.values.sum())
    }
    println(occurences.values.sum())
}

fun splitNumbers(occurences: MutableMap<Long, Long>) {
    val (even, odd) = (occurences.filter { it.value > 0 }.keys - 0).partition {
        "$it".length % 2L == 0L
    }
    val newNumbersEven: List<Pair<Long, Long>> = even.flatMap { eachEven ->
        val newNumbers = splitNumber(eachEven)
        newNumbers.map { newNumber ->
            newNumber to occurences[eachEven]!!
        } + listOf(eachEven to 0)
    }
    val newNumbersOdd: List<Pair<Long, Long>> = odd.flatMap { eachOdd ->
        val new = eachOdd * 2024
        listOf(
            new to occurences[eachOdd]!!,
            eachOdd to 0
        )
    }

    val zeroAndOne: List<Pair<Long, Long>> = listOf(
        1L to (occurences[0] ?: 0L),
        0L to 0L
    )

    val allnew: Map<Long, List<Pair<Long, Long>>> = (newNumbersEven + newNumbersOdd + zeroAndOne).groupBy { it.first }

    val summed: List<Pair<Long, Long>> = allnew.map { entry: Map.Entry<Long, List<Pair<Long, Long>>> ->
        if (entry.value.size == 1) {
            entry.key to entry.value.first().second
        } else {
            entry.key to entry.value.sumOf { it.second }
        }
    }

    occurences.putAll(summed)
}

fun List<Long>.blink() = flatMap {
    blink(it)
}

private fun blink(it: Long): List<Long> = if (it == 0L) {
    listOf(1)
} else if ("$it".length % 2 == 0) {
    splitNumber(it)
} else {
    listOf(it * 2024)
}

private fun splitNumber(long: Long): List<Long> {
    val middle = "$long".length / 2
    return listOf(
        "$long".substring(0..<middle),
        "$long".substring(middle..("$long".lastIndex))
    )
        .map { it.toLong() }
}


data class Stone(var value: Long = 0) {

    fun blink(): List<Stone> = if (value == 0L) {
        value++
        listOf(this)
    } else if ("$value".length % 2 == 0) {
        val middle = "$value".length / 2
        listOf(
            "$value".substring(0..<middle),
            "$value".substring(middle..("$value".lastIndex))
        )
            .map { Stone(it.toLong()) }
    } else {
        value *= 2024
        listOf(this)
    }
}