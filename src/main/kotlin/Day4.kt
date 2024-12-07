package nl.swilvan

import java.io.File
import kotlin.math.min

fun main() {
    val input = File("src/main/resources/day4input").readLines().map { it.split("").filter { it.isNotEmpty() } }
//    println(countMatches(input))

    println(`countX-Mas`(input))
}


fun `countX-Mas`(matrix: List<List<String>>): Int =
    matrix.foldIndexed(0) { startY: Int, acc: Int, strings: List<String> ->
        acc + strings.foldIndexed(0) { startX: Int, a: Int, s: String ->
            a +  if ((getLinksBovenNaarRechtsOnder(matrix, startY-2, startX-2, strings, "MAS") ||
                    getLinksBovenNaarRechtsOnder(matrix, startY-2, startX-2, strings, "SAM") ) &&
                    (getLinksOnderNaarRechtsBoven( startY+2, startX-2, strings, "MAS", matrix)||
                            getLinksOnderNaarRechtsBoven(startY+2, startX-2, strings, "SAM", matrix))
                ){
                1
            }else 0
        }
    }



fun countMatches(matrix: List<List<String>>): Int =
    matrix.foldIndexed(0) { startY: Int, acc: Int, strings: List<String> ->
        acc + strings.foldIndexed(0) { startX: Int, a: Int, s: String ->
            a +
                    matchHorizontal(matrix, startX, startY) +
                    matchVertical(matrix, startX, startY) +
                    matchDiagonal(matrix, startX, startY, "X", "S", "MAS", "AMX")
        }
    }

fun matchHorizontal(matrix: List<List<String>>, startX: Int, startY: Int): Int {
    val currentLine = matrix[startY]
    if ("X" == currentLine[startX]) {
        return if (currentLine.size >= startX + 4 && "MAS" == currentLine.subList(startX + 1, startX + 4)
                .joinToString(separator = "")
        ) {
            println("match X at $startY, $startX")
            1
        } else 0
    } else if ("S" == currentLine[startX]) {
        return if (currentLine.size >= startX + 4 && "AMX" == currentLine.subList(startX + 1, startX + 4)
                .joinToString(separator = "")
        ) {
            println("match S at $startY, $startX")
            1
        } else 0
    } else {
        return 0
    }
}

fun matchVertical(matrix: List<List<String>>, startX: Int, startY: Int): Int {
    val currentLine = matrix[startY]
    if ("X" == currentLine[startX]) {
        return if (matrix.size >= startY + 4 && "MAS" == (1..3).joinToString("") { matrix[startY + it][startX] }) {
            println("match X at $startY, $startX")
            1
        } else 0
    }
    if ("S" == currentLine[startX]) {
        return if (matrix.size >= startY + 4 && "AMX" == (1..3).joinToString("") { matrix[startY + it][startX] }) {
            println("match S at $startY, $startX")
            1
        } else 0
    }
    return 0
}

fun matchDiagonal(
    matrix: List<List<String>>,
    startX: Int,
    startY: Int,
    startLetter: String,
    endLetter: String,
    ltrRest: String,
    rtlRest: String
): Int {
    val currentLine = matrix[startY]
    if (startLetter == currentLine[startX]) {
        return countDiagonalLeftToRight(matrix, startY, startX, currentLine, ltrRest)
    }
    if (endLetter == currentLine[startX]) {
        val linksBovenNaarRechtsOnder = getLinksBovenNaarRechtsOnder(matrix, startY, startX, currentLine, rtlRest)
        val linksOnderNaarRechtsBoven = getLinksOnderNaarRechtsBoven(startY, startX, currentLine, rtlRest, matrix)
        return if (linksBovenNaarRechtsOnder && linksOnderNaarRechtsBoven) {
            println("double match S at $startY, $startX")
            2
        } else if (linksBovenNaarRechtsOnder || linksOnderNaarRechtsBoven) {
            println("match S at $startY, $startX")
            1
        } else 0
    }
    return 0
}

private fun countDiagonalLeftToRight(
    matrix: List<List<String>>,
    startY: Int,
    startX: Int,
    currentLine: List<String>,
    matchString: String
): Int {
    val linksBovenNaarRechtsOnder = getLinksBovenNaarRechtsOnder(matrix, startY, startX, currentLine, matchString)
    val linksOnderNaarRechtsBoven = getLinksOnderNaarRechtsBoven(startY, startX, currentLine, matchString, matrix)
    return if (linksBovenNaarRechtsOnder && linksOnderNaarRechtsBoven) {
        println("double match X at $startY, $startX")
        2
    } else if (linksBovenNaarRechtsOnder || linksOnderNaarRechtsBoven) {
        println("match X at $startY, $startX")
        1
    } else 0
}

private fun getLinksOnderNaarRechtsBoven(
    startY: Int,
    startX: Int,
    currentLine: List<String>,
    matchString: String,
    matrix: List<List<String>>
) :Boolean{
    val length = matchString.length
    return if (startY >= length && startX + length+1 <= currentLine.size) {
        matchString == (1..length).joinToString("") { matrix[startY - it][startX + it] }
    } else false
}
private fun getLinksBovenNaarRechtsOnder(
    matrix: List<List<String>>,
    startY: Int,
    startX: Int,
    currentLine: List<String>,
    matchString: String
):Boolean {
    val length = matchString.length

    return if (matrix.size >= startY + length+1 && startX + length+1 <= currentLine.size && startY>= -1 &&startX >= -1)
        matchString == (1..length).joinToString("") { matrix[startY + it][startX + it] }
    else false
}
