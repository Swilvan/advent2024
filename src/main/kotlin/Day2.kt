package nl.swilvan

import java.io.File
import kotlin.math.abs


fun main(){

    val safe =File("src/main/resources/day2input").readLines()
        .map { it.split(" ").map { it.toInt() } }
        .filter(::ascendingOrDescending)
        .filter(::diffNeverMoreThan3lessThan1)

        .size

    println(safe)
}

fun diffNeverMoreThan3lessThan1(ints: List<Int>):Boolean {
    val diffCorrect = getDiffCorrect(ints)
    return diffCorrect.success || getDiffCorrect(ints.slice(ints.indices.minus(diffCorrect.failingIdx))).success
}

private fun getDiffCorrect(ints: List<Int>): ListTestResult {
    var failingIdx = -1
    val success = ints.foldIndexed(ints[0] - 1) { idx, acc, curr ->
        if (abs(curr - acc) in 1..3) curr else {
            if(failingIdx == -1) failingIdx = idx
            Int.MIN_VALUE
        }
    } > Int.MIN_VALUE
    return ListTestResult(success, failingIdx)
}
fun ascendingOrDescending(ints: List<Int>): Boolean {
    if(ints.isEmpty()) return true

    val asc = isAscending(ints)

    val desc = isDescending(ints)

//    println("${asc.failingIdx} ${desc.failingIdx}")

    return asc.success ||
            desc.success ||
            isAscending(ints.slice(ints.indices.minus(asc.failingIdx-1))).success ||
            isDescending(ints.slice(ints.indices.minus(desc.failingIdx-1))).success ||
            isAscending(ints.subList(0, ints.size-1)).success ||
            isDescending(ints.subList(0, ints.size-1)).success
}

private fun isDescending(ints: List<Int>): ListTestResult {
    var failingIdx = -1
    val desc = ints.foldIndexed(ints[0] + 1) { idx, acc, curr ->
        if (curr < acc) curr else {
            if(failingIdx == -1) failingIdx = idx
            Int.MIN_VALUE
        }
    } > Int.MIN_VALUE
    return ListTestResult(desc, failingIdx)
}
private fun isAscending(ints: List<Int>): ListTestResult {
    var failingIdx = -1
    val asc = ints.foldIndexed(ints[0] - 1) { idx, acc, curr ->
        if (curr > acc) curr else {
            if (failingIdx == -1) failingIdx = idx
            Int.MAX_VALUE
        }
    } < Int.MAX_VALUE

    return ListTestResult(asc, failingIdx)
}
data class ListTestResult(val success: Boolean, val failingIdx: Int)