package com.nawilny.aoc2021.day09

import com.nawilny.aoc2021.common.Input

private data class Point(val x: Int, val y: Int)

private class HeightMap(lines: List<String>) {

    val map = Array(lines.size) { i ->
        lines[i].map { it.digitToInt() }.toIntArray()
    }

    fun getHeight(p: Point) = map[p.y][p.x]

    fun getAllPoints(): List<Point> {
        return map.indices.flatMap { y ->
            map[y].indices.map { x ->
                Point(x, y)
            }
        }
    }

    fun getNeighbours(p: Point): List<Point> {
        val neighbours = mutableListOf<Point>()
        if (p.x > 0) neighbours.add(Point(p.x - 1, p.y))
        if (p.x < map[p.y].size - 1) neighbours.add(Point(p.x + 1, p.y))
        if (p.y > 0) neighbours.add(Point(p.x, p.y - 1))
        if (p.y < map.size - 1) neighbours.add(Point(p.x, p.y + 1))
        return neighbours
    }

    fun calculateBasin(lowPoint: Point): Set<Point> {
        val basinPoints = mutableSetOf<Point>()
        val pointsToAnalyze = mutableListOf(lowPoint)
        while (pointsToAnalyze.isNotEmpty()) {
            val p = pointsToAnalyze.removeFirst()
            getNeighbours(p)
                .filter { !basinPoints.contains(it) || getHeight(it) < 9 }
                .forEach {
                    basinPoints.add(it)
                    pointsToAnalyze.add(it)
                }
        }

        return basinPoints
    }
}

fun main() {
    val lines = Input.readFileLines("day09", "input.txt").filter { it.isNotBlank() }

    val map = HeightMap(lines)
    val lowPoints = map.getAllPoints().filter { p ->
        val h = map.getHeight(p)
        map.getNeighbours(p).all { map.getHeight(it) > h }
    }

    println(lowPoints.sumOf { map.getHeight(it) + 1 })
    val top3basins = lowPoints.map { map.calculateBasin(it) }.map { it.size }.sortedDescending().take(3)
    println(top3basins)
    println(top3basins.fold(1) { acc, i -> acc * i })
}
