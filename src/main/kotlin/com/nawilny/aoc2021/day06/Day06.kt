package com.nawilny.aoc2021.day06

import com.nawilny.aoc2021.common.Input

private fun tick(state: Map<Int, Long>): Map<Int, Long> {
    val newState = (0..8).associateWith { 0L }.toMutableMap()
    var newFishWithSix = 0L
    state.forEach { (key, value) ->
        if (key == 0) {
            newFishWithSix = value
            newState[8] = value
        } else {
            newState[key - 1] = value
        }
    }
    newState[6] = newState[6]!! + newFishWithSix
    return newState
}

fun main() {
    val line = Input.readFileLines("day06", "input.txt").first().split(",").map { it.toInt() }
    val initialState = (0..8).associateWith { 0L }.toMutableMap()
    line.forEach { age -> initialState.compute(age) { _, it -> it!! + 1 } }

    var state = initialState.toMap()
    println(state)
    (1..256).forEach {
        println(it)
        state = tick(state)
        println(state)
    }
    println("----------")
    println(state.toList().sumOf { it.second })
}
