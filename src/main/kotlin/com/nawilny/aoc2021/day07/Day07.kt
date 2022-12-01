package com.nawilny.aoc2021.day07

import com.nawilny.aoc2021.common.Input
import kotlin.math.abs

private fun calculateFuel1(positions: List<Int>, targetPosition: Int): Int {
    return positions.sumOf { abs(it - targetPosition) }
}

private fun calculateFuel2(positions: List<Int>, targetPosition: Int): Int {
    return positions
        .map { abs(it - targetPosition) }
        .map {
            ((it + 1) * it) / 2
        }
        .sum()
}

fun main() {
    val positions = Input.readFileLines("day07", "input.txt").first().split(",").map { it.toInt() }

    val min = positions.minOrNull()!!
    val max = positions.maxOrNull()!!
    println("$min -> $max")

    val minFuel = (min..max).map {
        Pair(it, calculateFuel2(positions, it))
    }.minByOrNull { it.second }!!

    println("----------")
    println("pos ${minFuel.first} - fuel ${minFuel.second}")
}
