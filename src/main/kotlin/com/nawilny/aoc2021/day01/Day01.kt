package com.nawilny.aoc2021.day01

import com.nawilny.aoc2021.common.Input

fun main() {
    val lines = Input.readFileLines("day01", "input1.txt").filter { it.isNotBlank() }.map { it.toInt() }.toIntArray()
    var prev: Int? = null
    var increased = 0
    for (i in 0..(lines.size - 3)) {
        val sum = lines[i] + lines[i+1] + lines[i+2]
        if (prev != null && prev < sum) {
            increased++
        }
        prev = sum
    }
    println(increased)
}
