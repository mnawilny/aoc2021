package com.nawilny.aoc2021.day03

import com.nawilny.aoc2021.common.Input

fun main() {
    val report = Input.readFileLines("day03", "input.txt").filter { it.isNotBlank() }
        .map { s -> s.map { it.digitToInt() }.toIntArray() }
    val length = report.first().size

    val gammaRate = IntArray(length)
    val epsilonRate = IntArray(length)
    (0 until length).forEach { i ->
        val zeroes = report.count { it[i] == 0 }
        if (zeroes > report.size / 2) {
            gammaRate[i] = 0
            epsilonRate[i] = 1
        } else {
            gammaRate[i] = 1
            epsilonRate[i] = 0
        }
    }
    println(toDecimal(gammaRate) * toDecimal(epsilonRate))

    println("------------")
    val oxygenRating = calculateRating(report, false)
    val co2Rating = calculateRating(report, true)
    println(toDecimal(oxygenRating))
    println(toDecimal(co2Rating))
    println(toDecimal(oxygenRating) * toDecimal(co2Rating))

}

private fun toDecimal(intArray: IntArray): Int {
    return intArray.reversed().withIndex().fold(Pair(0, 1)) { acc, i ->
        Pair(acc.first + (i.value * acc.second), acc.second * 2)
    }.first
}

private fun calculateRating(report: List<IntArray>, reverse: Boolean): IntArray {
    var candidates = report
    var i = 0
    while (candidates.size > 1) {
        candidates = filterCandidates(candidates, i++, reverse)
    }
    return candidates.first()
}

private fun filterCandidates(candidates: List<IntArray>, i: Int, reverse: Boolean): List<IntArray> {
    val zeroes = candidates.count { it[i] == 0 }
    var mostCommon = when {
        zeroes < candidates.size / 2 -> 1
        zeroes > candidates.size / 2 -> 0
        else -> 1
    }
    if (reverse) {
        mostCommon = when (mostCommon) {
            0 -> 1
            else -> 0
        }
    }
    return candidates.filter { it[i] == mostCommon }
}
