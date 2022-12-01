package com.nawilny.aoc2021.day10

import com.nawilny.aoc2021.common.Input

private class Stack<T> {

    val s = mutableListOf<T>()

    fun push(e: T) {
        s.add(e)
    }

    fun pop() = if (s.isEmpty()) null else s.removeLast()

    fun remains() = s.reversed()
}

private val scores = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137
)

private val openingChars = setOf('(', '[', '{', '<')
private val charsPairs = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')

private fun getError(s: String): Char? {
    val stack = Stack<Char>()
    for (c in s) {
        if (openingChars.contains(c)) {
            stack.push(c)
        } else {
            val pop = stack.pop()
            if (pop == null || c != charsPairs[pop]) {
                return c
            }
        }
    }
    return null
}

private fun getCompletion(s: String): List<Char> {
    val stack = Stack<Char>()
    for (c in s) {
        if (openingChars.contains(c)) {
            stack.push(charsPairs[c]!!)
        } else {
            stack.pop()
        }
    }
    return stack.remains()
}

private fun calculateCompletionScore(l: List<Char>): Long {
    return l.fold(0L) { acc, c ->
        (acc * 5) + when (c) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> throw IllegalStateException("Unsupported closing character '$c'")
        }
    }
}

fun main() {
    val lines = Input.readFileLines("day10", "input.txt").filter { it.isNotBlank() }

    val result1 = lines.mapNotNull { getError(it) }.sumOf { scores[it]!! }
    println(result1)

    val completionScores = lines.filter { getError(it) == null }
        .map { getCompletion(it) }
        .map { calculateCompletionScore(it) }
    println(completionScores)

    println(completionScores.sorted()[completionScores.size / 2])
}
