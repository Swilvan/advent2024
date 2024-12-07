package nl.swilvan

import java.io.File

fun main() {
    val input = File("src/main/resources/day7input").readLines()
    val equations = parseInput(input)

    val validEquations: List<Equation> = equations.filter { it.canWork() }

    println(validEquations.sumOf { it.result })

}

fun parseInput(input: List<String>): List<Equation> =
    input.map { it.split(":") }
        .map { Equation(it[0].toLong(), it[1].trim().split(" ").map { it.toLong() }) }


data class Equation(val result: Long, val factors: List<Long>) {

    fun canWork(): Boolean = recurse()

    fun recurse(symbols: List<String> = listOf(), remainingFactors: List<Long> = factors): Boolean {
        if (remainingFactors.size == 1) {
            return remainingFactors[0] == result
        }

        val first = remainingFactors[0]
        val second = remainingFactors[1]
        val plusbranch = if (first + second <= result) {
            recurse(symbols + "+", listOf(first + second) + remainingFactors.drop(2))
        } else
            false

        val timesbranch = if (first * second <= result) {
            recurse(symbols + "*", listOf((first * second)) + remainingFactors.drop(2))
        } else
            false

        val concatbranch = if ("$first$second".toLong() <= result) {
            recurse(symbols + "||", listOf("$first$second".toLong()) + remainingFactors.drop(2))
        } else false

        return plusbranch || timesbranch || concatbranch
    }
}