fun main() {
    val input = generateSequence(::readLine)
    val lines = input.toList()
    val numbers = mutableListOf<Int>()
    val max = { numbers.maxOrNull() ?: throw NumberFormatException() }

    for (line in lines) {
        line.replace("\\s+".toRegex(), " ").split(" ").map { it.toInt() }.forEach { numbers.add(it) }
    }

    println("Total numbers: ${numbers.size}.")
    println("The greatest number: ${max()} (${numbers.count { it == max() }} time(s)).")
}