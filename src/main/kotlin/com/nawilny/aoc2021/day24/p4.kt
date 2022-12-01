package com.nawilny.aoc2021.day24

private fun validate(input: IntArray): Boolean {
    var w = 0
    var x = 0
    var z = 0
    z = (z * 26) + input[0] + 1
    z = (z * 26) + input[1] + 10
    z = (z * 26) + input[2] + 2
    w = input[3]
    x = (z % 26) - 10
    z /= 26
    if (x != w)
        z = (z * 26) + w + 5
    w = input[4]
    z = (z * 26) + w + 6
    w = input[5]
    z = (z * 26) + w
    w = input[6]
    z = (z * 26) + w + 16
    w = input[7]
    x = (z % 26) - 11
    z /= 26
    if (x != w)
        z = (z * 26) + w + 12
    w = input[8]
    x = (z % 26) - 7
    z /= 26
    if (x != w)
        z = (z * 26) + w + 15
    w = input[9]
    z = (z * 26) + w + 7
    w = input[10]
    x = (z % 26) - 13
    z /= 26
    if (x != w)
        z = (z * 26) + w + 6
    w = input[11]
    x = (z % 26)
    z /= 26
    if (x != w)
        z = (z * 26) + w + 5

//    z - 12, in - 2 -> z = 8
//    z - 12, in - 3 -> z = 9
//    z - 13, in - 1 -> z = 7
//    z - 13, in - 3 -> z = 9
//    z - 14, in - 1 -> z = 7
//    z - 14, in - 2 -> z = 8
//    z - 40, in - 3 -> z = 1

    w = input[12]
    x = (z % 26) - 11
    z /= 26
    if (x != w)
        z = (z * 26) + w + 6




    if (z == input[13]) {
        return true
    } else {
        return false
    }
}

fun main() {
    val input = IntArray(14) { 1 }
    val r = validate(input)
    println(r)
}
