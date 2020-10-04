fun main(args: Array<String>) {
    val sort = args.contains("-sortIntegers")
    val command =
        if (sort) "long" else if (args.isNotEmpty() && args.size == 2 && args[0] == "-dataType") args[1] else "word"
    val lines = generateSequence(::readLine).toList()

    when (command) {
        "long" -> numbers(lines, sort)
        "line" -> lines(lines)
        "word" -> words(lines)
    }
}

fun numbers(lines: List<String>, sort: Boolean) {
    val numbers = mutableListOf<Long>()
    val max = { numbers.maxOrNull() ?: throw NumberFormatException() }
    val count = { numbers.count { it == max() } }

    for (line in lines) filterLine(line).map { it.toLong() }.forEach { numbers.add(it) }
    println("Total numbers: ${numbers.size}.")
    if (!sort) println("The greatest number: ${max()} (${count()} time(s), ${count() * 100 / numbers.size}%).") else {
        val numSort = numbers.toTypedArray()
        mergeSort(numSort)
        print("Sorted data: ")
        for (i in numSort.indices) print("${numSort[i]}" + if (i != numSort.lastIndex) " " else "")
        println()
    }
}

fun lines(lines: List<String>) {
    val max = { lines.maxByOrNull { it.length } ?: throw Exception() }
    val count = { lines.count { it == max() } }

    println("Total lines: ${lines.size}.")
    println("The longest line:")
    println(max())
    println("(${count()} time(s), ${count() * 100 / lines.size}%).")
}

fun words(lines: List<String>) {
    var words = mutableListOf<String>()
    val max = { words.maxByOrNull { it.length } ?: throw Exception() }
    val count = { words.count { it == max() } }

    for (line in lines) filterLine(line).map { it }.forEach { words.add(it) }
    words = words.sorted().toMutableList()
    println("Total words: ${words.size}.")
    println("The longest word: ${max()} (${count()} time(s), ${count() * 100 / words.size}%).")
}

fun filterLine(line: String) = line.replace("\\s+".toRegex(), " ").split(" ")

// modified from https://www.geeksforgeeks.org/merge-sort/
fun mergeSort(numbers: Array<Long>, left: Int = 0, right: Int = numbers.lastIndex) {
    if (left < right) {
        val middle = (left + right) / 2

        mergeSort(numbers, left, middle)
        mergeSort(numbers, middle + 1, right)
        merge(numbers, left, middle, right)
    }
}

fun merge(numbers: Array<Long>, left: Int, middle: Int, right: Int) {
    val sub1 = middle - left + 1
    val sub2 = right - middle
    val leftTemp = Array(sub1) { 0L }
    val rightTemp = Array(sub2) { 0L }
    var i = 0
    var j = 0
    var k = left

    for (i1 in 0 until sub1) leftTemp[i1] = numbers[left + i1]
    for (j1 in 0 until sub2) rightTemp[j1] = numbers[middle + 1 + j1]
    while (i < sub1 && j < sub2) {
        if (leftTemp[i] <= rightTemp[j]) {
            numbers[k] = leftTemp[i]
            i++
        } else {
            numbers[k] = rightTemp[j]
            j++
        }
        k++
    }
    while (i < sub1) {
        numbers[k] = leftTemp[i]
        i++
        k++
    }
    while (j < sub2) {
        numbers[k] = rightTemp[j]
        j++
        k++
    }
}