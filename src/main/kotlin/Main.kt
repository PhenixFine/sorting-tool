import java.io.File

var INPUT = ""
var OUTPUT = ""

fun main(args: Array<String>) {
    val sortCount = args.contains("-sortingType") && args.contains("byCount")
    if (commandsOkay(args)) {
        val lines = if (INPUT == "") generateSequence(::readLine).toList() else File(INPUT).readLines()

        when {
            args.contains("long") -> numbers(lines, sortCount)
            args.contains("line") -> lines(lines, sortCount)
            else -> words(lines, sortCount)
        }
    }
}

fun commandsOkay(args: Array<String>): Boolean {
    val sortType = listOf("byCount", "natural")
    val dataType = listOf("long", "line", "word")

    for (i in args.indices) {
        val last = i == args.lastIndex

        when (args[i]) {
            "-sortingType" -> if (last || !sortType.contains(args[i + 1])) {
                println("No sorting type defined!")
                return false
            }
            "-dataType" -> if (last || !dataType.contains(args[i + 1])) {
                println("No sorting type defined!")
                return false
            }
            "-inputFile" -> if (last || !File(args[i + 1]).isFile) {
                println("${args[i + 1]} is not a file.")
                return false
            } else INPUT = args[i + 1]
            "-outputFile" -> if (last) {
                println("A filename was not given.")
                return false
            } else try {
                File(args[i + 1]).writeText("")
                OUTPUT = args[i + 1]
            } catch (e: Exception) {
                println(
                    "There was an error in writing to your file. Please ensure it is not set to read only or open" +
                            "in another program."
                )
                return false
            }
            else -> if (!(sortType + dataType).contains(args[i]) && args[i] != INPUT && args[i] != OUTPUT)
                println("\"${args[i]}\" is not a valid parameter. It will be skipped.")
        }
    }
    return true
}

fun numbers(lines: List<String>, sortCount: Boolean) {
    val numbers = mutableListOf<Long>()
    val error = { it: String -> println("\"$it\" is not a long. It will be skipped."); null }

    for (line in lines) filterLine(line).map { it.toLongOrNull() ?: error(it) }
        .forEach { if (it != null) numbers.add(it) }
    output("Total numbers: ${numbers.size}.\n")
    if (sortCount) {
        val percent = { count: Int -> count * 100 / numbers.size }
        val mapCount = mutableMapOf<Int, MutableList<Long>>()
        for (number in numbers) {
            val count = numbers.count { it == number }
            if (mapCount.containsKey(count) && !mapCount[count]?.contains(number)!!) mapCount[count]?.add(number) else {
                mapCount[count] = mutableListOf(number)
            }
        }
        val counts = mapCount.keys.toList().sorted()
        for (count in counts) {
            val numbs = mapCount[count]?.sorted() ?: break
            for (numb in numbs) output("$numb: $count time(s), ${percent(count)}%\n")
        }
    } else {
        numbers.sort()
        printSort()
        printList(numbers)
    }
}

fun lines(lines: List<String>, sortCount: Boolean) {
    output("Total lines: ${lines.size}.\n")
    if (sortCount) {
        printStringsCount(lines)
    } else {
        val hold = lines.sorted()
        printSort("\n")
        printList(hold, true)
    }
}

fun words(lines: List<String>, sortCount: Boolean) {
    val words = mutableListOf<String>()

    for (line in lines) filterLine(line).map { it }.forEach { words.add(it) }
    output("Total words: ${words.size}.\n")
    if (sortCount) {
        printStringsCount(words)
    } else {
        words.sort()
        printSort()
        printList(words)
    }
}

fun filterLine(line: String) = line.replace("\\s+".toRegex(), " ").split(" ")

fun printSort(add: String = "") = output("Sorted data: $add")

fun printList(list: List<Any>, line: Boolean = false) {
    for (i in list.indices) output("${list[i]}" + if (i != list.lastIndex) if (line) "\n" else " " else "")
    output("\n")
}

fun printStringsCount(strings: List<String>) {
    val percent = { count: Int -> count * 100 / strings.size }
    val mapCount = mutableMapOf<Int, MutableList<String>>()

    for (string in strings) {
        val count = strings.count { it == string }
        if (mapCount.containsKey(count) && !mapCount[count]?.contains(string)!!) mapCount[count]?.add(string) else {
            mapCount[count] = mutableListOf(string)
        }
    }
    val counts = mapCount.keys.toList().sorted()
    for (count in counts) {
        val strings2 = mapCount[count]?.sorted() ?: break
        for (string in strings2) output("$string: $count time(s), ${percent(count)}%\n")
    }
}

fun output(string: String) = if (OUTPUT == "") print(string) else File(OUTPUT).appendText(string)