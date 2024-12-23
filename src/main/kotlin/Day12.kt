package nl.swilvan

import nl.swilvan.grid.Cell
import nl.swilvan.grid.Grid
import nl.swilvan.grid.Grid.Companion.gridOf
import java.io.File

fun main() {
    val input = File("src/main/resources/day12input").readLines()
    val grid = gridOf(input) { it }

    val areas = grid.uniqueValues.flatMap {
        grid.findAreaOf(it)
    }
    println(areas.sumOf { a -> a.size * a.sumOf { it.perimeter() } })
}

fun <T> Cell<T>.perimeter(): Int = 4 - neighbours.filter { it.value == this.value }.size

fun <T> Grid<T>.findAreaOf(t: T): Set<Set<Cell<T>>> {
    val valueEquals: (Cell<T>) -> Boolean = { it.value == t }
    val remainingCells = cells.filter(valueEquals).toMutableList()
    var result: Set<Set<Cell<T>>> = setOf()

    while (remainingCells.any(valueEquals)) {
        val startpoint = remainingCells.find(valueEquals)

        val area: Set<Cell<T>> = startpoint!!.walk(neighbourPredicate = valueEquals)
        remainingCells.removeAll(area)

        result = result.plusElement(area)
    }

    return result
}

fun <T> Cell<T>.walk(
    area: MutableSet<Cell<T>> = mutableSetOf(this),
    neighbourPredicate: (Cell<T>) -> Boolean
): Set<Cell<T>> {
    this.seen = true
    var selectedNeighbours = neighbours
        .filter(neighbourPredicate)
        .filter{!it.seen }
    area.addAll(selectedNeighbours)
    selectedNeighbours.forEach { it.seen = true }

    while (selectedNeighbours.isNotEmpty()) {
        val newSelectedNeighbours = selectedNeighbours
            .flatMap {
                it.neighbours
                    .filter(neighbourPredicate)
                    .filter { !it.seen }
            }
        newSelectedNeighbours.forEach { it.seen = true }
        area += newSelectedNeighbours
        selectedNeighbours = newSelectedNeighbours
    }
    return area.toSet()
}