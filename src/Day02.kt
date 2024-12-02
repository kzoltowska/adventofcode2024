private typealias Report = List<Int>

fun main() {

    fun convert(input: List<String>): List<List<Int>> =
        input.map { it.split(" ").map { it.toInt() } }

    fun isSafe(report: Report): Boolean = with (report.windowed(2).map { (a, b) -> b - a }) {
        return this.all { it in 1..3 } || this.all { it in -3..-1 }
    }

    fun part1(input: List<Report>): Int =
        input.count { isSafe(it) }

    fun isSafeWithProblemDampener(report: Report): Boolean =
        report.withIndex().any { (reportIndex, _) ->
            isSafe(report.subList(0, reportIndex) + report.subList(reportIndex + 1, report.size))
        }

    fun part2(input: List<Report>): Int =
        input.count { isSafeWithProblemDampener(it) }

    val testInput = convert(readInput("Day02_test"))
    check(part1(testInput) == 2)
    val part2 = part2(testInput)
    check(part2 == 4)

    val input = convert(readInput("Day02"))
    part1(input).println()
    part2(input).println()
}
