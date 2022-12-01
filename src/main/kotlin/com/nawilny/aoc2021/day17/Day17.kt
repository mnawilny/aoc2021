package com.nawilny.aoc2021.day17

private data class Point(val x: Int, val y: Int)

private data class Target(val xRange: IntRange, val yRange: IntRange) {
    val maxX = xRange.maxOrNull()!!
    val minY = yRange.minOrNull()!!
    fun isIn(p: Point) = xRange.contains(p.x) && yRange.contains(p.y)
    fun isOvershoot(p: Point) = p.x > maxX || p.y < minY
}

private fun hits(initialVX: Int, initialVY: Int, target: Target): Int? {
    var point = Point(0, 0)
    var vx = initialVX
    var vy = initialVY
    var maxY = Int.MIN_VALUE
    while (!target.isOvershoot(point)) {
        if (target.isIn(point)) {
            return maxY
        }
        point = Point(point.x + vx, point.y + vy)
        if (maxY < point.y) {
            maxY = point.y
        }
        vx = when {
            vx < 0 -> vx + 1
            vx > 0 -> vx - 1
            vx == 0 -> vx
            else -> error("This is not possible")
        }
        vy -= 1
    }
    return null
}

fun main() {
//    val target = Target(20..30, -10..-5)
    val target = Target(288..330, -96..-50)
//    println(hits(7, 2, target))
//    println(hits(6, 3, target))
//    println(hits(9, 0, target))
//    println(hits(17, -4, target))
//    println(hits(6, 9, target))
    val p = (1..1000).map { Pair(it, hits(25, it, target)) }.filter { it.second != null }.maxByOrNull { it.second!! }
    println(p)

    val c = (1..1000).sumOf { vx ->
        (-1000..1000).map { hits(vx, it, target) }.count { it != null }
    }
    println(c)
}
