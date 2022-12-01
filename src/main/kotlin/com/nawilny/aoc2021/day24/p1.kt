package com.nawilny.aoc2021.day24

fun main() {
    val input = IntArray(14) { 1 }
    var z = 0
    z = (z * 26) + input[0] + 1
    z = (z * 26) + input[1] + 10
    z = (z * 26) + input[2] + 2
    z /= 26
    if ((z % 26) - 10 != input[3]) {
        z = (z * 26) + input[3] + 5
    }
    z = (z * 26) + input[4] + 6
    z = (z * 26) + input[5]
    z = (z * 26) + input[6] + 16
    z /= 26
    if ((z % 26) - 11 != input[7]) {
        z = (z * 26) + input[7] + 12
    }
    z /= 26
    if ((z % 26) - 7 != input[8]) {
        z = (z * 26) + input[8] + 15
    }
    z = (z * 26) + input[9] + 7
    z /= 26
    if ((z % 26) - 13 != input[10]) {
        z = (z * 26) + input[10] + 6
    }
    z /= 26
    if ((z % 26) != input[11]) {
        z = (z * 26) + input[11] + 5
    }
    z /= 26
    if ((z % 26) - 11 != input[12]) {
        z = (z * 26) + input[12] + 6
    }
    z /= 26
    // input[13] == z % 26
    if ((z % 26) != input[13]) {
        z = (z * 26) + input[13] + 15
    }
    println(z)
    println(100 % 26)
    println((100 / 26) % 26)
}
