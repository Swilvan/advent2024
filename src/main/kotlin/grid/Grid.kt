package nl.swilvan.grid


class Grid<T>(grid: List<List<T>>) {

    val cells: List<Cell<T>> = grid.flatMapIndexed { y, row ->
        row.mapIndexed { x, c -> Cell(c, x, y) }
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

data class Cell<T>(val value: T, val x: Int, val y: Int) {
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