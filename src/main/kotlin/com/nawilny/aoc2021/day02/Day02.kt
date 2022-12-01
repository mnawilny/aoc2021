package com.nawilny.aoc2021.day02

import com.nawilny.aoc2021.common.Input

private enum class Direction {
    FORWARD, DOWN, UP
}

private data class Command(val direction: Direction, val distance: Int) {

    fun move(p: Position): Position {
        return when (direction) {
            Direction.FORWARD -> Position(p.horizontal + distance, p.depth + (p.aim * distance), p.aim)
            Direction.DOWN -> Position(p.horizontal, p.depth, p.aim + distance)
            Direction.UP -> Position(p.horizontal, p.depth, p.aim - distance)
        }
    }

    companion object {
        fun parse(s: String): Command {
            val parts = s.split(" ")
            return Command(Direction.valueOf(parts[0].uppercase()), parts[1].toInt())
        }
    }
}

private data class Position(val horizontal: Int, val depth: Int, val aim: Int)

fun main() {
    val result = Input.readFileLines("day02", "input.txt").filter { it.isNotBlank() }
        .map { Command.parse(it) }
        .fold(Position(0, 0, 0)) { acc, it -> it.move(acc) }
    println(result)
    println(result.horizontal * result.depth)
}
