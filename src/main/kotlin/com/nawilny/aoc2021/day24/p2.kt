package com.nawilny.aoc2021.day24

fun main() {
    val input = IntArray(14) { 1 }
    var w = 0
    var x = 0
    var z = 0
    w = input[0]
    x = (z % 26) + 11
    x = if (x == w) 0 else 1
    z *= (25 * x) + 1
    z += (w + 1) * x
    w = input[1]
    x = (z % 26) + 10
    x = if (x == w) 0 else 1
    z *= (25 * x) + 1
    z += (w + 10) * x
    w = input[2]
    x = (z % 26) + 13
    x = if (x == w) 0 else 1
    z *= (25 * x) + 1
    z += (w + 2) * x
    w = input[3]
    x = (z % 26) - 10
    z /= 26
    x = if (x == w) 0 else 1
    z *= (25 * x) + 1
    z += (w + 5) * x
    w = input[4]
    x = (z % 26) + 11
    x = if (x == w) 0 else 1
    z *= (25 * x) + 1
    z += (w + 6) * x
    w = input[5]
    x = (z % 26) + 11
    x = if (x == w) 0 else 1
    z *= (25 * x) + 1
    z += w * x
    w = input[6]
    x = (z % 26) + 12
    x = if (x == w) 0 else 1
    z *= (25 * x) + 1
    z += (w + 16) * x
    w = input[7]
    x = (z % 26) - 11
    z /= 26
    x = if (x == w) 0 else 1
    z *= (25 * x) + 1
    z += (w + 12) * x
    w = input[8]
    x = (z % 26) - 7
    z /= 26
    x = if (x == w) 0 else 1
    z *= (25 * x) + 1
    z += (w + 15) * x
    w = input[9]
    x = (z % 26) + 13
    x = if (x == w) 0 else 1
    z *= (25 * x) + 1
    z += (w + 7) * x
    w = input[10]
    x = (z % 26) - 13
    z /= 26
    x = if (x == w) 0 else 1
    z *= (25 * x) + 1
    z += (w + 6) * x
    w = input[11]
    x = (z % 26)
    z /= 26
    x = if (x == w) 0 else 1
    z *= (25 * x) + 1
    z += (w + 5) * x
    w = input[12]
    x = (z % 26) - 11
    z /= 26
    x = if (x == w) 0 else 1
    z *= (25 * x) + 1
    z += (w + 6) * x
    w = input[13]
    x = (z % 26)
    z /= 26
    x = if (x == w) 0 else 1
    z *= (25 * x) + 1
    z += (w + 15) * x
}
