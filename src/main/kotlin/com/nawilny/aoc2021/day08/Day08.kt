package com.nawilny.aoc2021.day08

import com.nawilny.aoc2021.common.Input

private fun discoverDigits(signals: List<String>): Map<String, Int> {
    val one = signals.first { it.length == 2 }
    val four = signals.first { it.length == 4 }
    val seven = signals.first { it.length == 3 }
    val eight = signals.first { it.length == 7 }
    val fiveLong = signals.filter { it.length == 5 }
    val sixLong = signals.filter { it.length == 6 }
    val nine = sixLong.first { it.contains(four) }
    val zero = sixLong.first { it.contains(one) && !it.contains(four) }
    val six = sixLong.first { !it.contains(one) }
    val three = fiveLong.first { it.contains(one) }
    val five = fiveLong.minus(three).first { it.contains(four, 3) }
    val two = fiveLong.minus(five).minus(three).first()
    return mapOf(
        zero to 0,
        one to 1,
        two to 2,
        three to 3,
        four to 4,
        five to 5,
        six to 6,
        seven to 7,
        eight to 8,
        nine to 9
    ).mapKeys { it.key.sort() }
}

fun main() {
    val lines = Input.readFileLines("day08", "input.txt").filter { it.isNotBlank() }

//    val result = lines.map { it.split("|") }.map { it[1] }.flatMap { it.split(" ") }.filter { it.isNotBlank() }
//        .filter { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }
//        .count()

    val result = lines.map { it.split("|") }.map { lineParts ->
        val digits = lineParts[0].split(" ").filter { it.isNotBlank() }
        val translated = discoverDigits(digits)
        val number = lineParts[1].split(" ").filter { it.isNotBlank() }.map { translated[it.sort()] }
        (number[0]!! * 1000) + (number[1]!! * 100) + (number[2]!! * 10) + number[3]!!
    }.sum()
    println(result)
}

private fun String.contains(s: String, numberOfChars: Int? = null): Boolean {
    return if (numberOfChars == null) {
        s.all { this.contains(it) }
    } else {
        s.count { this.contains(it) } == numberOfChars
    }
}

private fun String.sort(): String {
    return this.toList().sorted().joinToString(separator = "")
}
