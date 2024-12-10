package nl.swilvan.grid


class Grid<T>(val grid: List<List<T>>) {

    val cells: List<Cell<T>>
    val maxX: Int
    val maxY: Int

    init {
        maxX = (grid.firstOrNull()?.size?.minus(1)) ?: -1
        maxY = grid.size - 1
        val tempCells = grid.mapIndexed { y, row ->
            row.mapIndexed { x, c -> Cell(c, x, y) }
        }
        cells = tempCells.flatMap() { row ->
            row.map { cell ->
                val possibleLocations = listOf(
                    cell.x + 1 to cell.y,
                    cell.x - 1 to cell.y,
                    cell.x to cell.y + 1,
                    cell.x to cell.y - 1
                ).filter { (0..maxX).contains(it.first) && (0..maxY).contains(it.second) }
                cell.copy(neighbours = possibleLocations.map {
                    tempCells[it.first][it.second]
                })
            }
        }
    }

    companion object {
        fun gridOfInt(fileLines: List<String>) = gridOf(fileLines) { str -> str.toInt() }

        fun <T> gridOf(fileLines: List<String>, transform: (String) -> T): Grid<T> {
            val grid = Grid(fileLines.map { fileLine -> fileLine.split("").filter { it.isNotBlank() }.map(transform) })
            println("Creating y: ${fileLines.size} * x: ${fileLines.firstOrNull()?.length ?: 0} grid")
            println(grid)
            return grid
        }
    }

    fun getCell(x: Int, y: Int) = cells.first { it.x == x && it.y == y }

    fun findCellsWhere(cellValuePredicate: (T) -> Boolean): List<Cell<T>> =
        cells.filter { cellValuePredicate.invoke(it.value) }

    override fun equals(other: Any?): Boolean {
        return other is Grid<*> && cells == other.cells
    }

    override fun hashCode(): Int {
        return cells.hashCode()
    }

    override fun toString(): String =
        cells.groupBy { it.x }.map { it.value.map { cell -> cell.value }.joinToString(" ") }.joinToString("\n")
}

data class Cell<T>(val value: T, val x: Int, val y: Int, val neighbours: List<Cell<T>> = listOf()) {
    fun is2dNeighbour(other: Cell<T>): Boolean {
        val (_, x, y) = other
        return listOf(
            x - 1 == this.x && y == this.y,
            x + 1 == this.x && y == this.y,
            x == this.x && y + 1 == this.y,
            x == this.x && y - 1 == this.y
        ).any { it }
    }
}