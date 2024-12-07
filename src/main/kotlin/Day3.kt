package nl.swilvan

import java.io.File

fun main(){
    val listMul = File("src/main/resources/day3input").readLines().joinToString()
        .split("don\'t\\(\\).*?do\\(\\)".toRegex())
        .reduce(String::plus)
        .replace("don\'t\\(\\).*".toRegex(), "")
    val totalOfMultiplications = "mul\\((\\d+),(\\d+)\\)".toRegex().findAll(listMul).toList()
        .map { it.groupValues.slice(1..2) }
        .sumOf { it.map(String::toInt).reduce(Math::multiplyExact) }
    println(totalOfMultiplications)

}