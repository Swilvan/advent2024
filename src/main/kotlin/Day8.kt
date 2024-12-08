package nl.swilvan

import java.io.File

fun main() {
    val antennas = parseInputDay8(File("src/main/resources/day8input").readLines())

//    antennas.findAntinodes().groupBy { it.y }.toList().sortedBy { it.first }.forEach(::println)
    println(antennas.findAntinodes().size)
}

fun <T> uniquePairsOrderIndependent(list: List<T>): List<Pair<T, T>> =
    list.flatMapIndexed { idx, t ->
        List(list.size - (idx + 1)) { _ -> t }.zip(list.subList(idx + 1, list.size))
    }

data class Antenna(val id: Char, val position: Position) {
    operator fun minus(other: Antenna): Antenna = this.copy(position = this.position - other.position)
    operator fun plus(other: Antenna): Antenna = this.copy(position = this.position + other.position)
}

data class AntennaMap(val size: Position, val antennas: List<Antenna>) {

    fun findAntinodes(): Set<Position> = antennas.groupBy { it.id }
        .flatMap { entry ->
            uniquePairsOrderIndependent(entry.value)
                .flatMap { findAntinodes(it.first, it.second) }
        }.toSet()

    fun findAntinodes(antenna1: Antenna, antenna2: Antenna): Set<Position> {
        val positionDiff = antenna1.position - antenna2.position

        return if (antenna1.position + positionDiff != antenna2.position)
            allAntinodesFrom(antenna1, positionDiff) + allAntinodesFrom(antenna2, -positionDiff)
        else
            allAntinodesFrom(antenna1, -positionDiff) + allAntinodesFrom(antenna2, positionDiff)
    }

    fun allAntinodesFrom(antenna: Antenna, diff: Position): Set<Position> {
        val result = mutableSetOf<Position>()
        var newPosition = antenna.position
        while((0..<size.x).contains(newPosition.x) && (0..<size.y).contains(newPosition.y) ){
            result.add(newPosition)
            newPosition += diff
        }
        return result
    }
}




fun parseInputDay8(input: List<String>): AntennaMap =
    AntennaMap(
        Position(input[0].length, input.size),
        input.flatMapIndexed { y, row ->
            row.mapIndexed { x, char ->
                if (char.isLetterOrDigit()) Antenna(char, Position(x, y)) else null
            }
        }.filterNotNull()
    )