fun main(args: Array<String>) {
    val command = if (args.isNotEmpty() && args.size == 2 && args[0] == "-dataType") args[1] else "word"
    val input = generateSequence(::readLine)
    val lines = input.toList()

    when (command) {
        "long" -> numbers(lines)
        "line" -> lines(lines)
        "word" -> words(lines)
    }
}

fun numbers(lines: List<String>) {
    val numbers = mutableListOf<Long>()
    val max = { numbers.maxOrNull() ?: throw NumberFormatException() }
    val count = { numbers.count { it == max() } }

    for (line in lines) {
        line.replace("\\s+".toRegex(), " ").split(" ").map { it.toLong() }.forEach { numbers.add(it) }
    }
    println("Total numbers: ${numbers.size}.")
    println("The greatest number: ${max()} (${count()} time(s), ${count() * 100 / numbers.size}%).")
}

fun words(lines: List<String>) {
    var words = mutableListOf<String>()
    val max = { words.maxByOrNull { it.length } ?: throw Exception() }
    val count = { words.count { it == max() } }

    for (line in lines) {
        line.replace("\\s+".toRegex(), " ").split(" ").map { it }.forEach { words.add(it) }
    }
    words = words.sorted().toMutableList()
    println("Total words: ${words.size}.")
    println("The longest word: ${max()} (${count()} time(s), ${count() * 100 / words.size}%).")
}

fun lines(lines: List<String>) {
    val max = { lines.maxByOrNull { it.length } ?: throw Exception() }
    val count = { lines.count { it == max() } }

    println("Total lines: ${lines.size}.")
    println("The longest line:")
    println(max())
    println("(${count()} time(s), ${count() * 100 / lines.size}%).")
}