import java.util.Collections.*

private typealias Order = Pair<Int, Int>
private typealias Update = List<Int>

fun main() {
    fun convert(input: List<String>): Pair<List<Order>, List<Update>> {
        val orderings = mutableListOf<Order>()
        val updates = mutableListOf<Update>()
        input.forEach { line ->
            if (line.contains("|")) {
                val (a, b) = line.split("|").map { it.toInt() }
                orderings.add(Pair(a, b))
            } else if (line.isNotEmpty()) {
                updates.add(line.split(",").map { it.toInt() })
            }
        }
        return Pair(orderings, updates)
    }

    fun part1(orderings: List<Order>, updates: List<Update>): Int {
        val map = orderings
            .groupBy { it.first }
            .map { (key, order) -> key to order.map { it.second }.toSet() }
            .toMap()

        fun check(update: Update): Boolean {
            val nums = mutableSetOf<Int>()

            update.forEach { n ->
                val rules = map.getOrDefault(n, emptySet())
                if (!disjoint(nums, rules)) {
                    return false
                }
                nums.add(n)
            }

            return true
        }

        return updates.filter { check(it) }.sumOf { it[it.size / 2] }
    }

    fun part2(orderings: List<Order>, updates: List<Update>): Int {
        val map = orderings
            .groupBy { it.first }
            .map { (key, order) -> key to order.map { it.second }.toSet() }
            .toMap()

        fun check(update: Update): Boolean {
            val nums = mutableSetOf<Int>()

            update.forEach { n ->
                val rules = map.getOrDefault(n, emptySet())
                if (!disjoint(nums, rules)) {
                    return false
                }
                nums.add(n)
            }

            return true
        }

        val orderingsSet = orderings.toSet()

        return updates
            .filterNot { check(it) }
            .map {
                it.sortedWith { a, b ->
                    when {
                        Pair(a, b) in orderingsSet -> 1
                        Pair(b, a) in orderingsSet -> -1
                        else -> 0
                    }
                }
            }
            .sumOf { it[it.size / 2] }
    }

    val testInput = readInput("Day05_test")
    val (orderings, updates) = convert(testInput)
    check(part1(orderings, updates) == 143)
    check(part2(orderings, updates) == 123)

    val input = readInput("Day05")
    val (orderings1, updates1) = convert(input)
    part1(orderings1, updates1).println()
    part2(orderings1, updates1).println()
}
