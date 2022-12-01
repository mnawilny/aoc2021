package com.nawilny.aoc2021.day05

import com.nawilny.aoc2021.common.Input
import kotlin.math.max
import kotlin.math.min

private data class Point(val x: Int, val y: Int)

private data class Line(val start: Point, val end: Point)

fun main() {
    val lines = Input.readFileLines("day05", "input.txt")
        .filter { it.isNotBlank() }
        .map { it.split(" -> ") }
        .map { lineParts ->
            val first = lineParts[0].split(",").map { it.toInt() }
            val second = lineParts[1].split(",").map { it.toInt() }
            Line(Point(first[0], first[1]), Point(second[0], second[1]))
        }
//    lines.forEach { println(it) }

    val ventsMap = lines.flatMap { line ->
        if (line.start.x == line.end.x) {
            (min(line.start.y, line.end.y)..(max(line.start.y, line.end.y)))
                .map { Point(line.start.x, it) }
        } else if (line.start.x < line.end.x) {
            val y = line.start.y
            val dy = when {
                line.start.y < line.end.y -> 1
                line.start.y > line.end.y -> -1
                else -> 0
            }
            (line.start.x..line.end.x).withIndex().map { Point(it.value, y + (dy * it.index)) }
        } else {
            val y = line.end.y
            val dy = when {
                line.end.y < line.start.y -> 1
                line.end.y > line.start.y -> -1
                else -> 0
            }
            (line.end.x..line.start.x).withIndex().map { Point(it.value, y + (dy * it.index)) }
        }
    }.fold(mutableMapOf<Point, Int>()) { acc, p ->
        acc.compute(p) { _, v -> if (v == null) 1 else v + 1 }
        acc
    }
    println(ventsMap.filter { it.value > 1 }.count())
}
