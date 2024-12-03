import java.util.regex.Pattern

fun main() {

    fun part1(input: String): Int {
        val pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)")
        val matcher = pattern.matcher(input)
        return matcher.results()
            .map { it.group(1).toInt() * it.group(2).toInt() }
            .reduce { x, y -> x + y }.get()
    }

    fun part2(input: String): Int {

        fun String.paddedSubstring(startIndex: Int, endIndex: Int): String =
            substring(startIndex, endIndex.coerceAtMost(length))

        var enable = true
        var i = 0
        var sum = 0

        fun String.accept(string: String): Boolean {
            if (paddedSubstring(i, string.length + i) == string) {
                i += string.length
                return true
            }
            return false
        }

        fun String.acceptInt(): Int? {
            val start = i
            while (input[i].isDigit()) i++

            return if (start == i) null else substring(start, i).toInt()
        }

        while (i < input.length) {
            if (enable && input.accept("mul")) {
                if (!input.accept("(")) continue

                val a = input.acceptInt() ?: continue

                if (!input.accept(",")) continue

                val b = input.acceptInt() ?: continue

                if (input.accept(")")) {
                    sum += a * b
                }
            } else if (input.accept("don't")) {
                enable = false
            } else if (input.accept("do")) {
                enable = true
            } else {
                i++
            }
        }

        return sum
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput.joinToString()) == 161)
    val part2 = part2("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")
    check(part2 == 48)

    val input = readInput("Day03")
    part1(input.joinToString()).println()
    part2(input.joinToString()).println()
}
