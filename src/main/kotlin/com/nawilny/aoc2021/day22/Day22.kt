package com.nawilny.aoc2021.day22

import com.nawilny.aoc2021.common.Input
import kotlin.math.abs
import kotlin.math.min

private data class Cuboid(val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {

    fun contains(x: Int, y: Int, z: Int): Boolean {
        return xRange.contains(x) && yRange.contains(y) && zRange.contains(z)
    }

    private fun subRange(r1: IntRange, r2: IntRange): List<Pair<IntRange, Boolean>>? {
        return if (r1.first < r2.first) {
            if (r1.last < r2.first) {
                null
            } else if (r1.last > r2.last) {
                listOf(
                    Pair(r1.first until r2.first, false),
                    Pair(r2.first..r2.last, true),
                    Pair(r2.last + 1..r1.last, false)
                )
            } else {
                listOf(
                    Pair(r1.first until r2.first, false),
                    Pair(r2.first..min(r1.last, r2.last), true)
                )
            }
        } else {
            if (r1.first > r2.last) {
                null
            } else if (r1.last > r2.last) {
                listOf(
                    Pair(r1.first..r2.last, true),
                    Pair(r2.last + 1..r1.last, false)
                )
            } else {
                listOf(Pair(r1, true))
            }
        }
    }

    fun subtract(c: Cuboid): List<Cuboid> {
        val xDivided = subRange(xRange, c.xRange)
        val yDivided = subRange(yRange, c.yRange)
        val zDivided = subRange(zRange, c.zRange)
        if (xDivided == null || yDivided == null || zDivided == null) {
            return listOf(this)
        }
        val result = mutableListOf<Cuboid>()
        for (x in xDivided) {
            if (x.second) {
                for (y in yDivided) {
                    if (y.second) {
                        for (z in zDivided) {
                            if (!z.second) {
                                result.add(Cuboid(x.first, y.first, z.first))
                            }
                        }
                    } else {
                        result.add(Cuboid(x.first, y.first, zRange))
                    }
                }
            } else {
                result.add(Cuboid(x.first, yRange, zRange))
            }
        }
        return result
    }

    fun length(r: IntRange) = (abs(r.last - r.first) + 1).toLong()

    fun countPoints() = length(xRange) * length(yRange) * length(zRange)

}

private data class Step(val cuboid: Cuboid, val value: Boolean) {
    fun contains(x: Int, y: Int, z: Int) = cuboid.contains(x, y, z)
}

private fun parseRange(s: String): IntRange {
    val p = s.split(".")
    return IntRange(p[0].toInt(), p[2].toInt())
}

private fun parseStep(s: String): Step {
    val parts = s.split(" ")
    val value = when (parts[0]) {
        "on" -> true
        "off" -> false
        else -> error("Invalid prefix '$parts[0]'")
    }
    val coordinates = parts[1].split(",")
    val x = parseRange(coordinates[0].substring(2))
    val y = parseRange(coordinates[1].substring(2))
    val z = parseRange(coordinates[2].substring(2))
    return Step(Cuboid(x, y, z), value)
}

private fun count1(steps: List<Step>): Long {
    val reversedSteps = steps.reversed()
    var counter = 0L
    for (x in -50..50) {
        for (y in -50..50) {
            for (z in -50..50) {
                if (reversedSteps.firstOrNull { it.contains(x, y, z) }?.value == true) {
                    counter++
                }
            }
        }
    }
    return counter
}

private fun count2(steps: List<Step>): Long {
    var litPoints = listOf<Cuboid>()
    steps.forEach { step ->
        litPoints = if (step.value) {
            val toAdd = litPoints.fold(listOf(step.cuboid)) { acc, i ->
                acc.flatMap { it.subtract(i) }
            }
            litPoints.plus(toAdd)
        } else {
            litPoints.flatMap { it.subtract(step.cuboid) }
        }
    }
    println(litPoints.size)
    return litPoints.sumOf { it.countPoints() }
}

fun main() {
    val steps = Input.readFileLines("day22", "input.txt").filter { it.isNotBlank() }.map { parseStep(it) }
    steps.forEach { println(it) }

    val start = System.currentTimeMillis()
    val counter = count2(steps)
    val end = System.currentTimeMillis()

    println("---------")
    println(counter)
    println("${(end - start) / 1000.0}s")
}
