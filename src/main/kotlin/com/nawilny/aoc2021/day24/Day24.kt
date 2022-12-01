package com.nawilny.aoc2021.day24

import com.nawilny.aoc2021.common.Input

private data class In(val s: String) {
    var i = 0
    fun get() = s[i++].digitToInt()
}

private enum class Op {
    INP, ADD, MUL, DIV, MOD, EQL
}

private data class Operation(val op: Op, val arg1: String, val arg2: String?)

private data class ALU(
    val operations: List<Operation>,
    var x: Int = 0,
    var y: Int = 0,
    var z: Int = 0,
    var w: Int = 0
) {

    private fun set(s: String, value: Int) {
        when (s) {
            "w" -> w = value
            "x" -> x = value
            "y" -> y = value
            "z" -> z = value
            else -> error("Invalid arg '$s'")
        }
    }

    private fun get(s: String): Int {
        return when (s) {
            "w" -> w
            "x" -> x
            "y" -> y
            "z" -> z
            else -> s.toInt()
        }
    }

    fun run(input: In) {
        w = 0
        x = 0
        y = 0
        z = 0
        operations.forEach { o ->
            when (o.op) {
                Op.INP -> set(o.arg1, input.get())
                Op.ADD -> set(o.arg1, get(o.arg1) + get(o.arg2!!))
                Op.MUL -> set(o.arg1, get(o.arg1) * get(o.arg2!!))
                Op.DIV -> set(o.arg1, get(o.arg1) / get(o.arg2!!))
                Op.MOD -> set(o.arg1, get(o.arg1) % get(o.arg2!!))
                Op.EQL -> set(o.arg1, if (get(o.arg1) == get(o.arg2!!)) 1 else 0)
            }
        }
    }

    fun runCompiled(input: IntArray) {
        z = 0
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
    }

}

private fun parse(s: String): Operation {
    val parts = s.split(" ")
    val op = when (parts[0]) {
        "inp" -> Op.INP
        "add" -> Op.ADD
        "mul" -> Op.MUL
        "div" -> Op.DIV
        "mod" -> Op.MOD
        "eql" -> Op.EQL
        else -> error("Unknown command '$parts[0]'")
    }
    val arg1 = parts[1]
    val arg2 = if (parts.size > 2) parts[2] else null

    println(
        when (op) {
            Op.INP -> "$arg1 = input.get()"
            Op.ADD -> "$arg1 += $arg2"
            Op.MUL -> "$arg1 *= $arg2"
            Op.DIV -> "$arg1 /= $arg2"
            Op.MOD -> "$arg1 %= $arg2"
            Op.EQL -> "$arg1 = if ($arg1 == $arg2) 1 else 0"
        }
    )

    return Operation(op, arg1, arg2)
}

fun main() {
    val operations = Input.readFileLines("day24", "input.txt").filter { it.isNotBlank() }.map { parse(it) }
    val alu = ALU(operations)
    var i = 89999999999999L
    alu.run(In(i.toString()))
    while (alu.z != 0) {
        if (i % 10000000 == 0L) {
            println(i)
        }
        i--
        val s = i.toString()
        i.toString()
        if (s.contains("0")) {
            continue
        }
        alu.runCompiled(s.map { it.digitToInt() }.toIntArray())
    }

//    val start = System.currentTimeMillis()
//    for (i in 99999999999999L downTo 99999999000000L) {
//        val s = i.toString()
//        if (s.contains("0")) {
//            continue
//        }
//        alu.reset()
//        alu.runCompiled(In(s))
//    }
//    val end = System.currentTimeMillis()
//    println("${(end - start).toDouble() / 1000}s")


//    println(alu)
//    println(alu.z)
//    println(i)
    // less than 99999841500000
    // 8 too low
//    85000000000000
    // 9 too high
}
