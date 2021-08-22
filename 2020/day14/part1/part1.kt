import java.io.File

fun readLines(fileName: String): List<String> {
    return File(fileName).readLines()
}

fun applyBitmask(bitmask: String, originalValue: Int): Long {
    val originalBinaryString: String = Integer.toBinaryString(originalValue)
    var newBinaryString: String = ""

    for ((i, bit) in bitmask.reversed().withIndex()) {
        newBinaryString += if (i > originalBinaryString.length - 1 && bit in listOf('0', '1')) {
            bit
        } else if (i > originalBinaryString.length - 1) {
            '0'
        } else {
            when (bit) {
                '0' -> '0'
                '1' -> '1'
                else -> originalBinaryString.reversed()[i]
            }
        }
    }
    if (bitmask.length != newBinaryString.length) {
        throw Exception("Length of original Binary and new Binary cannot be different")
    }
    return newBinaryString.reversed().toLong(2)
}

fun main() {
    val fileName: String = "input.txt"
    val content: List<String> = readLines(fileName)
    val memoryMap: MutableMap<Int, Long> = mutableMapOf()
    lateinit var bitMap: String
    for ((i, line) in content.withIndex()) {
        val firstWord: String = line.split(" = ")[0]

        if (firstWord != "mask") {
            val indexValue: Int = firstWord.replace("[^0-9]".toRegex(), "").toInt()
            val originalValue: Int = line.split(" = ")[1].toInt()
            if (bitMap.isBlank()) {
                throw Exception("bitMap still blank!")
            }
            val newValue: Long = applyBitmask(bitMap, originalValue)
            memoryMap[indexValue] = newValue

        }
        else if (firstWord == "mask") {
            bitMap = line.split(" = ")[1]
        }
    }

    println(memoryMap.map { it.value }.sum())
}