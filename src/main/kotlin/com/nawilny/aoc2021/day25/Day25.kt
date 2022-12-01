package com.nawilny.aoc2021.day25

import com.nawilny.aoc2021.common.Input

data class Point(val x: Int, val y: Int)

private class Map(val input: List<String>) {

    val sizeX = input.first().length
    val sizeY = input.size
    val cucumbersE = input.withIndex().flatMap { line ->
        line.value.withIndex().filter { it.value == '>' }.map { Point(it.index, line.index) }
    }.toMutableSet()
    val cucumbersS = input.withIndex().flatMap { line ->
        line.value.withIndex().filter { it.value == 'v' }.map { Point(it.index, line.index) }
    }.toMutableSet()

    fun print() {
        for (y in 0 until sizeY) {
            for (x in 0 until sizeX) {
                val p = Point(x, y)
                print(
                    if (cucumbersE.contains(p)) {
                        '>'
                    } else if (cucumbersS.contains(p)) {
                        'v'
                    } else {
                        '.'
                    }
                )
            }
            println()
        }
    }

    fun nextE(p: Point) = if (p.x < sizeX - 1) Point(p.x + 1, p.y) else Point(0, p.y)
    fun nextS(p: Point) = if (p.y < sizeY - 1) Point(p.x, p.y + 1) else Point(p.x, 0)

    fun moveE() = move(cucumbersE) { nextE(it) }

    fun moveS() = move(cucumbersS) { nextS(it) }

    private fun move(cucumbers: MutableSet<Point>, nextFunction: (Point) -> Point): Boolean {
        val moves = cucumbers.mapNotNull { p ->
            val p2 = nextFunction(p)
            if (cucumbersE.contains(p2) || cucumbersS.contains(p2)) {
                null
            } else {
                Pair(p, p2)
            }
        }
        if (moves.isEmpty()) {
            return false
        }
        moves.forEach {
            cucumbers.remove(it.first)
            cucumbers.add(it.second)
        }
        return true
    }

}

fun main() {
    val lines = Input.readFileLines("day25", "input.txt").filter { it.isNotBlank() }
    val map = Map(lines)
    map.print()

    var done = false
    var i = 0
    while(!done) {
        val e = map.moveE()
        val s = map.moveS()
        done = !e && !s
        i++
    }
    println("----------------")
    map.print()
    println(i)
}
