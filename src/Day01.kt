import kotlin.math.abs

fun main() {
    fun convert(input: List<String>): List<Pair<Int, Int>> = input.map {
        val (a, b) = it.split("   ")
        Pair(a.toInt(), b.toInt())
    }

    fun sort(input: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
        val a = input.map { it.first }.sorted()
        val b = input.map { it.second }.sorted()
        return a.zip(b)
    }

    fun part1(input: List<Pair<Int, Int>>): Int =
        input.sumOf { abs(it.second - it.first) }

    fun part2(input: List<Pair<Int, Int>>): Int {
        val b = input.map { it.second }.groupingBy { it }.eachCount()
        return input.sumOf { it.first * b.getOrDefault(it.first, 0) }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = sort(convert(readInput("Day01_test")))
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = sort(convert(readInput("Day01")))
    part1(input).println()
    part2(input).println()
}
