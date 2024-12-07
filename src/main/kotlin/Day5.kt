package nl.swilvan

import java.io.File

fun main() {
    val input = File("src/main/resources/day5input").readLines()
    val pageOrderMap = pageOrderRegister(input)
    val pageOrderings = pageOrderings(input)

    val sumOfMiddleOfCorrectOrderings = getSumOfMiddleOfCorrectOrderings(pageOrderings, pageOrderMap)
    println(sumOfMiddleOfCorrectOrderings)

    val sumOfReorderedIncorrect =
        reorder(pageOrderings.filter { !correctOrdering(pageOrderMap, it) }, pageOrderMap).sumOf { it[it.size / 2] }
    println(sumOfReorderedIncorrect)
}

fun reorder(incorrectOrderings: List<List<Int>>, pageOrder: Map<Int, List<Int>>): List<List<Int>> =
    incorrectOrderings.map {
        it.sortedWith { a, b ->
            if (pageOrder[a]?.contains(b) == true) -1
            else if (pageOrder[b]?.contains(a) == true) 1
            else 0
        }
    }

fun getSumOfMiddleOfCorrectOrderings(
    pageOrderings: List<List<Int>>,
    pageOrderMap: Map<Int, List<Int>>
) = pageOrderings.sumOf { ordering ->
    if (correctOrdering(pageOrderMap, ordering)) ordering[(ordering.size) / 2]
    else 0
}

fun correctOrdering(pageOrderMap: Map<Int, List<Int>>, ordering: List<Int>): Boolean {
    val seen: MutableList<Int> = mutableListOf()
    ordering.forEach {
        if (pageOrderMap[it]?.any(seen::contains) == true) {
            return false
        }
        seen += it
    }
    return true
}

private val pageOrderRegex = "(\\d+)\\|(\\d+)".toRegex()

fun pageOrderRegister(input: List<String>): Map<Int, List<Int>> =
    input.filter { it.matches(pageOrderRegex) }
        .flatMap { pageOrderRegex.findAll(it).toList().map { it.groupValues.slice(1..2) } }
        .map { it.map(String::toInt).toList() }
        .groupBy({ it[0] }, { it[1] })


private val pageOrderingRegex = "(\\d+,?)+".toRegex()
fun pageOrderings(input: List<String>): List<List<Int>> =
    input.filter { it.matches(pageOrderingRegex) }
        .map { it.split(",") }
        .map { it.map { it.toInt() } }.toList()

