package com.nawilny.aoc2021.day11

import com.nawilny.aoc2021.common.Input

private data class Point(val x: Int, val y: Int)

private class OctopusesMap(lines: List<String>) {

    val map = Array(lines.size) { i ->
        lines[i].map { it.digitToInt() }.toIntArray()
    }

    fun getValue(p: Point) = map[p.y][p.x]

    fun incValue(p: Point) {
        map[p.y][p.x] = map[p.y][p.x] + 1
    }

    fun resetValue(p: Point) {
        map[p.y][p.x] = 0
    }

    fun getAllPoints(): List<Point> {
        return map.indices.flatMap { y ->
            map[y].indices.map { x ->
                Point(x, y)
            }
        }
    }

    fun getNeighbours(p: Point): List<Point> {
        val neighbours = mutableListOf<Point>()
        if (p.x > 0) {
            if (p.y > 0) neighbours.add(Point(p.x - 1, p.y - 1))
            neighbours.add(Point(p.x - 1, p.y))
            if (p.y < map.size - 1) neighbours.add(Point(p.x - 1, p.y + 1))
        }
        if (p.x < map[p.y].size - 1) {
            if (p.y > 0) neighbours.add(Point(p.x + 1, p.y - 1))
            neighbours.add(Point(p.x + 1, p.y))
            if (p.y < map.size - 1) neighbours.add(Point(p.x + 1, p.y + 1))
        }
        if (p.y > 0) neighbours.add(Point(p.x, p.y - 1))
        if (p.y < map.size - 1) neighbours.add(Point(p.x, p.y + 1))
        return neighbours
    }

    fun flash(p: Point): Int {
        if (getValue(p) <= 9) {
            return 0
        }
        resetValue(p)
        return getNeighbours(p).fold(1) { acc, i ->
            acc + if (getValue(i) > 0) {
                incValue(i)
                flash(i)
            } else {
                0
            }
        }
    }

    fun step(): Int {
        getAllPoints().forEach { incValue(it) }
        return getAllPoints().fold(0) { acc, p -> acc + flash(p) }
    }

    fun print() {
        println("----------------")
        map.forEach { line ->
            line.forEach { print(it) }
            println()
        }
        println("----------------")
    }
}

fun main() {
    val lines = Input.readFileLines("day11", "input.txt").filter { it.isNotBlank() }

    val map = OctopusesMap(lines)

//    var flashes = 0
//    (1..100).forEach { _ ->
//        flashes += map.step()
//        map.print()
//    }
//    println(flashes)

    val size = map.getAllPoints().size
    var i = 1
    while (map.step() != size) {
        i++
    }
    println(i)

}
