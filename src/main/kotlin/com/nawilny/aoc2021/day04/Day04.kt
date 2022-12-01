package com.nawilny.aoc2021.day04

import com.nawilny.aoc2021.common.Input

private class Board(val numbers: Array<IntArray>) {

    val marked = Array(numbers.size) { BooleanArray(numbers[0].size) }

    fun mark(n: Int) {
        for (row in numbers.withIndex()) {
            for (col in row.value.withIndex()) {
                if (col.value == n) {
                    marked[row.index][col.index] = true
                }
            }
        }
    }

    fun isWinner(): Boolean {
        if (marked.find { row -> row.all { it } } != null) {
            return true
        }
        for (i in marked[0].indices) {
            var won = true
            for (row in marked) {
                if (!row[i]) {
                    won = false
                }
            }
            if (won) {
                return true
            }
        }
        return false
    }

    fun unmarkedNumbers(): List<Int> {
        val result = mutableListOf<Int>()
        for (row in numbers.withIndex()) {
            for (col in row.value.withIndex()) {
                if (!marked[row.index][col.index]) {
                    result.add(col.value)
                }
            }
        }
        return result
    }

    fun print() {
        numbers.forEach { println(it.joinToString(", ")) }
        println()
    }

    companion object {
        fun parse(lines: List<String>): Board {
            val boardNumbers = lines.withIndex().fold(listOf<IntArray>()) { acc, line ->
                acc.plus(line.value.split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toIntArray())
            }
            return Board(boardNumbers.toTypedArray())
        }
    }
}

fun main() {
    val lines = Input.readFileLines("day04", "input.txt")
    val numbers = lines.first().split(",").map { it.toInt() }


    val boards = lines.subList(1, lines.size).fold(Pair(listOf<Board>(), listOf<String>())) { acc, line ->
        var boards = acc.first
        var boardLines = acc.second
        if (line.isBlank() && boardLines.size == 5) {
            boards = boards.plus(Board.parse(boardLines))
            boardLines = listOf()
        }
        if (line.isNotBlank()) {
            boardLines = boardLines.plus(line)
        }
        Pair(boards, boardLines)
    }.first

    // part 1
//    for (n in numbers) {
//        var winner: Board? = null
//        for (board in boards) {
//            board.mark(n)
//            if (winner == null && board.isWinner()) {
//                winner = board
//            }
//        }
//        if (winner != null) {
//            println(winner.unmarkedNumbers().sum())
//            println(n)
//            println(n * winner.unmarkedNumbers().sum())
//            break
//        }
//    }

    // part 2
    val winners = mutableListOf<Board>()
    for (n in numbers) {
        var lastWinner: Board? = null
        for (board in boards.minus(winners)) {
            board.mark(n)
            if (board.isWinner()) {
                winners.add(board)
                lastWinner = board
            }
        }
        if (winners.size == boards.size) {
            println(lastWinner!!.unmarkedNumbers().sum())
            println(n)
            println(n * lastWinner!!.unmarkedNumbers().sum())
            break
        }
    }
}
