package com.nawilny.aoc2021.day24


fun main() {
//    val input = IntArray(14) { 1 }
    //           01234567891111
    //                     0123
    val input = "12911816171712".map { it.digitToInt() }.toIntArray()
    if (input[2] != input[3] + 8) {
        error("invalid 1")
    }
    if (input[6] + 5 != input[7]) {
        error("invalid 2")
    }
    if (input[5] - 7 != input[8]) {
        error("invalid 3")
    }
    if (input[9] - 6 != input[10]) {
        error("invalid 4")
    }
    if (input[4] + 6 != input[11]) {
        error("invalid 5")
    }
    if (input[1] - 1 != input[12]) {
        error("invalid 6")
    }
    if (input[0] + 1 != input[13]) {
        error("invalid 7")
    }


//    println(z)
//    println(z == 751392684)
    // 751392684
}
