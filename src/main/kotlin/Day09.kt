package nl.swilvan

import java.io.File
fun List<FileEncoding>.checkSum() = fold(CheckSum(0, 0)) { acc, curr ->
    if(curr.id == -1L ) acc
    else
    CheckSum(
        result = acc.result + (acc.blockIdx..<acc.blockIdx + curr.size).sumOf { it * curr.id },
        blockIdx = acc.blockIdx + curr.size
    )
}
fun main(args: Array<String>) {
    val input = File("src/main/resources/day9input").readLines()[0]
    val parsed = parseDay9(input)

    val defragmented = defragment(parsed)

    print(
        defragmented.map { fe -> List(fe.size.toInt()){ fe.id}.joinToString("") } .joinToString("")
    )


    println(defragmented.checkSum())
}

fun defragment(parseDay9: MutableList<FileEncoding>): List<FileEncoding> {
    val fileEncodings = recurseFragmented(parseDay9)
    return fileEncodings
}

fun recurseFullFiles(
    idx: Int= 0,
    fragments: MutableList<FileEncoding>,
    defrag: MutableList<FileEncoding> = fragments.reversed().toMutableList(),
): List<FileEncoding> {
    if (idx == defrag.size)
        return fragments

    if( idx % 2 != 0) {
        val defragHead = defrag.removeAt(idx/2)
        val fittingFragmentIdx = fragments.indexOfFirst { it.id <0 && it.size >= defragHead.size  }
        if (fittingFragmentIdx >= 0){
            fragments.removeAt(fittingFragmentIdx)
            fragments.remove(defragHead)
            fragments.add(fittingFragmentIdx, defragHead)
        } else{
            fragments.add(fragments.size - idx, defragHead)
        }
    }
    return recurseFullFiles(idx + 1, fragments,defrag)
}
fun recurseFragmented(
    fragments: MutableList<FileEncoding>,
    defrag: MutableList<FileEncoding> = fragments.reversed().filter { it.id >= 0 }.toMutableList(),
    result: MutableList<FileEncoding> = mutableListOf()
): List<FileEncoding> {
//    println("fragments: $fragments")
//    println("defrag: $defrag")
//    println("result: $result")
    if (fragments.isEmpty()) return result

    val head = fragments[0]

    return if (head.id >= 0) {
        fragments.remove(head)
        defrag.remove(head)
        result.add(head)
        recurseFragmented(fragments, defrag, result)
    }else {
        if (defrag.isEmpty()) return result
        val defragHead = defrag[0]
        if (defragHead.size > head.size) {
            fragments.remove(head)
            fragments.remove(defragHead)
            fragments.add(defragHead.copy(size = defragHead.size - head.size))
            defrag.remove(defragHead)
            defrag.add(0, defragHead.copy(size = defragHead.size - head.size))
            result.add(defragHead.copy(size = head.size))
            recurseFragmented(
                fragments,
                defrag ,
                result
            )
        } else if (defragHead.size < head.size) {
            fragments.remove(head)
            fragments.remove(defragHead)
            fragments.add(0,head.copy(size = head.size - defragHead.size))
            defrag.remove(defragHead)
            result.add(defragHead.copy(size = defragHead.size))
            recurseFragmented(
                fragments,
                defrag,
                result
            )
        } else {
            fragments.remove(head)
            fragments.remove(defragHead)
            defrag.remove(defragHead)
            result.add(defragHead)
            recurseFragmented(fragments, defrag, result)
        }
    }
}

fun parseDay9(st: String): MutableList<FileEncoding> = st.mapIndexed { idx, char ->
    if (idx % 2 == 0)
        FileEncoding((idx / 2).toLong(), char.digitToInt().toLong())
    else
        FileEncoding(-1, char.digitToInt().toLong())
}.filter { it.size != 0L }.toMutableList()

data class FileEncoding(val id: Long, val size: Long)
data class CheckSum(val result: Long, val blockIdx: Long)

fun List<Pair<Long, Long>>.toFileEncoding() = this.map { FileEncoding(it.first, it.second) }
