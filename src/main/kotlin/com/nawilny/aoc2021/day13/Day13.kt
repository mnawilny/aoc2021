package com.nawilny.aoc2021.day13

import com.nawilny.aoc2021.common.Input

private data class Point(val x: Int, val y: Int)

private fun foldX(points: Set<Point>, x: Int): Set<Point> {
    return points.map { if (it.x > x) Point((2 * x) - it.x, it.y) else it }.toSet()
}

private fun foldY(points: Set<Point>, y: Int): Set<Point> {
    return points.map { if (it.y > y) Point(it.x, (2 * y) - it.y) else it }.toSet()
}

private fun fold(points: Set<Point>, fold: String): Set<Point> {
    val i = fold.substring(2).toInt()
    return when {
        fold.startsWith("x=") -> foldX(points, i)
        fold.startsWith("y=") -> foldY(points, i)
        else -> throw IllegalStateException("Unknown fold $fold")
    }
}

private fun print(points: Set<Point>) {
    val maxX = points.maxOf { it.x }
    val maxY = points.maxOf { it.y }
    for (y in 0..maxY) {
        for (x in 0..maxX) {
            print(if (points.contains(Point(x, y))) "#" else ".")
        }
        println()
    }
}

fun main() {
    val lines = Input.readFileLines("day13", "input.txt").filter { it.isNotBlank() }

    val points = mutableSetOf<Point>()
    val folds = mutableListOf<String>()

    lines.forEach { line ->
        if (line.startsWith("fold along ")) {
            folds.add(line.substring("fold along ".length))
        } else {
            val parts = line.split(",").map { it.toInt() }
            points.add(Point(parts[0], parts[1]))
        }
    }

    println(fold(points, folds.first()).size)
    val afterFolding = folds.fold(points.toSet()) {acc, p -> fold(acc, p)}
    print(afterFolding)
    // FPEKBEJL
}
