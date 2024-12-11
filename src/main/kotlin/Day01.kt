package nl.swilvan

import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("src/main/resources/day1input")

    val left = mutableListOf<Int>()
    val right = mutableListOf<Int>()

    val fileLines = input.readLines().forEach {
        val numbers = it.trim().split("\\s+".toRegex())
        left.add(numbers[0].toInt())
        right.add(numbers[1].toInt())
    }

    left.sort()
    right.sort()

    //Part 1
    println(left.foldIndexed(0){
         index: Int, acc: Int, l: Int -> acc + abs(l - right[index])
    })


    val rightOccurences = right.groupBy { it }

    //Part 2
    print( left.map{ it * rightOccurences.getOrDefault(it, listOf()).size }.sum())
}
