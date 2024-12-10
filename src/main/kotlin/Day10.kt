package nl.swilvan

import nl.swilvan.grid.Cell
import nl.swilvan.grid.Coordinates
import nl.swilvan.grid.Grid
import java.io.File

fun main() {
    val input = File("src/main/resources/day10input").readLines()
    val grid = Grid.gridOfInt(input)
    val trailStarts = grid.findCellsWhere { it.value==0 }
    val trailEnds = trailStarts.map{trailEnds(it).toSet()}

    println(trailEnds.sumOf { it.size })
    println(trailStarts.sumOf{ trailStartRating(it) })
}


fun trailStartRating(coordinates: Coordinates, grid: Grid<Int>): Int {
    val startCell = grid.findCellsWhere { it.coordinates == coordinates }[0]
    return trailStartRating(startCell)
}

fun trailStartRating(start:Cell<Int>): Int =
    trailEnds(start).size

fun trailEnds(start: Cell<Int>): List<Cell<Int>> {
    val neighbours = start.neighbours.filter { it.value == start.value + 1 }
    return recurseTrailEnds(neighbours)
}

fun trailEnds(start: Coordinates, grid: Grid<Int>): List<Cell<Int>> {
    val startCell = grid.findCellsWhere { it.coordinates == start }[0]
    return trailEnds(startCell)
}

fun recurseTrailEnds(fromCells: List<Cell<Int>>): List<Cell<Int>> {
    val trailEnds = fromCells.filter { it.value == 9 }
    return if (trailEnds.isNotEmpty() || fromCells.isEmpty())
        trailEnds
    else
        recurseTrailEnds(fromCells.flatMap { it.neighbours }.filter { it.value == fromCells.first().value + 1 })
}