package com.nawilny.aoc2021.day15

import com.nawilny.aoc2021.common.Input

private data class Point(val x: Int, val y: Int)

private data class PointWithRisk(val point: Point, var risk: Int)

private data class Path(val points: List<Point>, val risk: Int) {
    fun last() = points.last()
    fun plus(p: PointWithRisk): Path {
        return Path(points.plus(p.point), risk + p.risk)
    }
}

private class CaveMap(lines: List<String>) {

    val riskMap = lines.withIndex().flatMap { line ->
        line.value.asIterable().withIndex().map { Pair(Point(it.index, line.index), it.value.digitToInt()) }
    }.toMap()

    fun getStart(): Point {
        if (!riskMap.contains(Point(0, 0))) error("Start not in a map!")
        return Point(0, 0)
    }

    fun getEnd(): Point {
        val maxX = riskMap.keys.maxOf { it.x }
        val maxY = riskMap.keys.maxOf { it.y }
        if (!riskMap.contains(Point(maxX, maxY))) error("End not in a map!")
        return Point(maxX, maxY)
    }

    private fun getNeighbours(p: Point): Set<Point> {
        return setOf(Point(p.x - 1, p.y), Point(p.x + 1, p.y), Point(p.x, p.y - 1), Point(p.x, p.y + 1))
    }

//    fun findLeastRiskyPath(start: Point, end: Point): Path {
//        val distances: MutableMap<Point, Path> = riskMap.keys
//            .associateWith { Path(listOf(), Int.MAX_VALUE) }.toMutableMap()
//        distances[start] = Path(listOf(start), 0)
//
//        val unvisited = distances.map { it.key }.toMutableSet()
//
//        var current = distances[start]!!
//        while (true) {
//            if (current.last() == end) {
//                return current
//            }
//            getNeighbours(current.last()).filter { unvisited.contains(it.point) }.forEach { n ->
//                if (distances[n.point]!!.risk > current.risk + n.risk) {
//                    distances[n.point] = current.plus(n)
//                }
//            }
//            unvisited.remove(current.last())
//            current = unvisited.minByOrNull { distances[it]!!.risk }.let { distances[it] }!!
//        }
//    }

    fun findLeastRiskyPath2(start: Point, end: Point): Int {
        val distances: MutableMap<Point, PointWithRisk> = riskMap.keys
            .associateWith { PointWithRisk(it, Int.MAX_VALUE) }.toMutableMap()
        distances[start]!!.risk = 0

        val unvisited = distances.map { it.key }.toMutableSet()
        val unvisitedSorted = unvisited.map { distances[it]!! }.toMutableList()

        while (true) {
            if (unvisited.size % 1000 == 0) {
                println(unvisited.size)
            }
            unvisitedSorted.sortBy { it.risk }
            val current = unvisitedSorted.first()
            unvisitedSorted.removeFirst()

            val risk = current.risk
            if (current.point == end) {
                return risk
            }
            getNeighbours(current.point).filter { unvisited.contains(it) }.forEach { n ->
                if (distances[n]!!.risk > risk + riskMap[n]!!) {
                    distances[n]!!.risk = risk + riskMap[n]!!
                }
            }
            unvisited.remove(current.point)
        }
    }
}

private fun increaseLine(l: String): String {
    return l.map { it.digitToInt() }.map { it + 1 }.map { if (it > 9) 1 else it }.joinToString("")
}

private fun enlarge(lines: List<String>): List<String> {
    val p1 = lines.map { line ->
        val part1 = increaseLine(line)
        val part2 = increaseLine(part1)
        val part3 = increaseLine(part2)
        val part4 = increaseLine(part3)
        line + part1 + part2 + part3 + part4
    }
    val p2 = p1.map { increaseLine(it) }
    val p3 = p2.map { increaseLine(it) }
    val p4 = p3.map { increaseLine(it) }
    val p5 = p4.map { increaseLine(it) }
    return p1 + p2 + p3 + p4 + p5
//    return p1 + p2 + p3 + p4
}

fun main() {
    val lines = Input.readFileLines("day15", "input.txt").filter { it.isNotBlank() }

    val map = CaveMap(enlarge(lines))
    val start = map.getStart()
    val end = map.getEnd()
    println(start)
    println(end)

    val startTime = System.currentTimeMillis()
    println(map.findLeastRiskyPath2(start, end))
    val endTime = System.currentTimeMillis()
    println("time: ${(endTime - startTime) / 1000f}s")
    //17s
    // 2 - 162s
    // 3 - 121s
    // 4 - 343s
    // 5 - 718s
}
