package com.nawilny.aoc2021.day21

private class Dice {
    var position = 0
    fun get() = ++position + ++position + ++position
}

private data class Player(val position: Int, val score: Int = 0) {
    fun move(distance: Int): Player {
        val p = (position + distance) % 10
        val s = score + p + 1
        return Player(p, s)
    }
}

private data class Game(val p1: Player, val p2: Player, val p1Turn: Boolean = true) {
    fun move(distance: Int): Game {
        return if (p1Turn) {
            Game(p1.move(distance), p2, false)
        } else {
            Game(p1, p2.move(distance), true)
        }
    }
}

private fun <T> MutableMap<T, Long>.addToCounter(key: T, value: Long = 1) {
    this.compute(key) { _, v -> if (v == null) value else v + value }
}

fun main() {
    val p1start = 8
    val p2start = 2

    val throwResults = (1..3).flatMap { t1 -> (1..3).map { Pair(t1, it) } }.fold(mutableMapOf<Int, Long>()) { acc, i ->
        (1..3).forEach {
            val sum = i.first + i.second + it
            acc.addToCounter(sum, 1)
        }
        acc
    }
    println(throwResults)

    val game = Game(Player(p1start - 1), Player(p2start - 1))

    var games = mapOf(game to 1L)
    var p1Wins = 0L
    var p2Wins = 0L
    while (games.isNotEmpty()) {
        val gamesAfterMove = mutableMapOf<Game, Long>()
        games.forEach { game ->
            throwResults.forEach { (result, instances) ->
                val g = game.key.move(result)
                val i = game.value * instances
//                val i = game.value.toDouble().pow(instances.toDouble()).toLong()
                if (g.p1.score >= 21) {
                    p1Wins += i
                } else if (g.p2.score >= 21) {
                    p2Wins += i
                } else {
                    gamesAfterMove.addToCounter(g, i)
                }
            }
        }
        games = gamesAfterMove
        println(games.values.sum())
    }
    println("---------")
    println(p1Wins)
    println(p2Wins)

//    var g = game
//    val dice = Dice()
//    while (g.p1.score < 1000 && g.p2.score < 1000) {
//        val p1diceThrow = dice.get()
//        g = Game(g.p1.move(p1diceThrow), g.p2)
//        if (g.p1.score < 1000) {
//            val p2diceThrow = dice.get()
//            g = Game(g.p1, g.p2.move(p2diceThrow))
//        }
//    }
//    println(g)
//    println(min(g.p1.score, g.p2.score) * dice.position)

}
