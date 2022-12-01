package com.nawilny.aoc2021.day20

import com.nawilny.aoc2021.common.Input

private data class Image(val points: Array<Array<Int>>, val default: Int) {

    fun print() {
        points.forEach { row ->
            println(row.map { if (it == 1) '#' else '.' }.joinToString(""))
        }
    }

    fun enhance(enhancementAlgorithm: IntArray): Image {
        val newDefault = if (default == 0) enhancementAlgorithm[0] else enhancementAlgorithm[511]
        val newPoints = Array(points.size + 2) { Array(points[0].size + 2) { newDefault } }
        for (y in points.indices) {
            for (x in points[0].indices) {
                val n = getNeighboursValue(x, y)
                newPoints[y + 1][x + 1] = enhancementAlgorithm[n]
            }
        }
        return Image(newPoints, newDefault)
    }

    fun countLitPixels() = points.sumOf { row -> row.count { it == 1 } }

    private fun getNeighboursValue(x: Int, y: Int): Int {
        return listOf(
            Pair(x - 1, y - 1),
            Pair(x, y - 1),
            Pair(x + 1, y - 1),
            Pair(x - 1, y),
            Pair(x, y),
            Pair(x + 1, y),
            Pair(x - 1, y + 1),
            Pair(x, y + 1),
            Pair(x + 1, y + 1),
        ).map {
            if (it.first < 0 || it.first >= points[0].size || it.second < 0 || it.second >= points.size) {
                default
            } else {
                points[it.second][it.first]
            }
        }.joinToString("").toInt(2)
    }

    companion object {
        fun parse(lines: List<String>): Image {
            val points = Array(lines.size + 4) { Array(lines.first().length + 4) { 0 } }
            lines.withIndex().forEach { line ->
                line.value.withIndex().forEach { c ->
                    points[line.index + 2][c.index + 2] = if (c.value == '#') 1 else 0
                }
            }
            return Image(points, 0)
        }
    }
}

fun main() {
    val lines = Input.readFileLines("day20", "input.txt").filter { it.isNotBlank() }
    val enhancementAlgorithm = lines.first().map { if (it == '#') 1 else 0 }.toIntArray()
    var image = Image.parse(lines.drop(1))

    (1..50).forEach { i ->
        println(i)
        image = image.enhance(enhancementAlgorithm)
    }

//    image.print()
    println(image.countLitPixels())
    //5255 - too high
    //5228 - too high
    //5225 - OK
}
