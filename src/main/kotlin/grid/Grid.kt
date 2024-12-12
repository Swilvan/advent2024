package nl.swilvan.grid


class Grid<T>(val grid: List<List<T>>) {

    val uniqueValues: Set<T>
    val cells: List<Cell<T>>
    val maxX: Int
    val maxY: Int

    init {
        maxX = (grid.firstOrNull()?.size?.minus(1)) ?: -1
        maxY = grid.size - 1
        val tempCells = grid.mapIndexed { y, row ->
            row.mapIndexed { x, c -> Cell(c, x, y) }
        }
        cells = tempCells.flatMap { row ->
            row.map { cell ->
                val (x, y) = cell.coordinates
                val possibleLocations = setOf(
                    x + 1 to y,
                    x - 1 to y,
                    x to y + 1,
                    x to y - 1
                ).filter { (0..maxX).contains(it.first) && (0..maxY).contains(it.second) }
                cell.neighbours = possibleLocations.map {
                    tempCells[it.second][it.first]
                }.toSet()
                cell
            }
        }
        uniqueValues = tempCells.flatMap { it.map { it.value } }.toSet()
    }

    companion object {
        fun gridOfInt(fileLines: List<String>) = gridOf(fileLines) { str -> str.toInt() }

        fun <T> gridOf(fileLines: List<String>, transform: (String) -> T): Grid<T> {
            val grid = Grid(fileLines.map { fileLine -> fileLine.split("").filter { it.isNotBlank() }.map(transform) })
            println("Creating y: ${fileLines.size} * x: ${fileLines.firstOrNull()?.length ?: 0} grid")
            return grid
        }
    }

    fun getCell(x: Int, y: Int) = cells.first { it.coordinates.x == x && it.coordinates.y == y }

    fun findCellsWhere(cellPredicate: (Cell<T>) -> Boolean): List<Cell<T>> =
        cells.filter(cellPredicate)

    override fun equals(other: Any?): Boolean {
        return other is Grid<*> && cells == other.cells
    }

    override fun hashCode(): Int {
        return cells.hashCode()
    }

    override fun toString(): String =
        cells.groupBy { it.coordinates.y }.map { it.value.map { cell -> cell.value }.joinToString(" ") }
            .joinToString("\n")

}

data class Cell<T>(val value: T, val coordinates: Coordinates, var neighbours: Set<Cell<T>> = setOf()) {

    constructor(value: T, x: Int, y: Int) : this(value, Coordinates(x, y))

    override fun equals(other: Any?): Boolean = other is Cell<*> &&
            value == other.value &&
            coordinates == other.coordinates


    override fun toString(): String {
        return "Cell($value, $coordinates, neighbours)"
    }

    override fun hashCode(): Int {
        var result = value?.hashCode() ?: 0
        result = 31 * result + coordinates.hashCode()
        return result
    }
}

data class Coordinates(val x: Int, val y: Int)