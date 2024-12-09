
fun main() {

    class Node(val x: Int, val y: Int, val char: Char) {
        override fun toString(): String = char.toString()
    }

    fun part1(input: List<String>): Int {
        val grid = input.mapIndexed { i, row ->
            row.mapIndexed { j, c -> Node(i, j, c) }
        }

        grid.forEach { row ->
            row.forEach { node -> print(node.char) }
            println()
        }

        val word = "XMAS"

        fun next(x: Int, y: Int, dx: Int, dy: Int, string: String = ""): Boolean {
            return when {
                string == word -> true
                string.length > word.length -> false
                else -> {
                    val next = grid.getOrNull(x + dx)?.getOrNull(y + dy) ?: return false

                    if (next.char !in word) return false

                    next(x + dx, y + dy, dx, dy, string + next.char)
                }
            }
        }

        var count = 0
        grid.forEach { row ->
            row.forEach { node ->
                if (node.char == word.first()) {
                    //println("start from $node (${node.y}, ${node.x})")
                    val directions = listOf(
                       -1 to -1, -1 to 0, -1 to 1,
                        0 to -1,           0 to 1,
                        1 to -1,  1 to 0,  1 to 1
                    )

                    directions.forEach { (directionX, directionY) ->
                        if (next(node.x, node.y, directionX, directionY, word.first().toString())) {
                            count++
                        }
                    }
                }
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val grid = input.mapIndexed { i, row ->
            row.mapIndexed { j, c -> Node(i, j, c) }
        }

        fun checkMAS(x: Int, y: Int): Boolean {
            // Centre is always A at this point.

            val topLeft = grid.getOrNull(x - 1)?.getOrNull(y + -1)?.char ?: return false
            val topRight = grid.getOrNull(x - 1)?.getOrNull(y + 1)?.char ?: return false
            val bottomLeft = grid.getOrNull(x + 1)?.getOrNull(y - 1)?.char ?: return false
            val bottomRight = grid.getOrNull(x + 1)?.getOrNull(y + 1)?.char ?: return false

            return ((topLeft == 'M' && bottomRight == 'S') || (topLeft == 'S' && bottomRight == 'M')) &&
                   ((topRight == 'M' && bottomLeft == 'S') || (topRight == 'S' && bottomLeft == 'M'))
        }

        var count = 0
        grid.forEach { row ->
            row.forEach { node ->
                if (node.char == 'A') {
                    if (checkMAS(node.x, node.y)) count++
                }
            }
        }

        return count
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
