package com.nawilny.aoc2021.day14

import com.nawilny.aoc2021.common.Input

private data class PairWithDepth(val pair: Pair<Char, Char>, val depth: Int)

private val cache = mutableMapOf<PairWithDepth, Map<Char, Long>>()

private fun getInsight(input: PairWithDepth, rules: Map<Pair<Char, Char>, Char>): Map<Char, Long> {
    if (cache.contains(input)) {
        return cache[input]!!
    }
    val r = if (input.depth == 0) {
        mapOf()
    } else if (!rules.contains(input.pair)) {
        mapOf()
    } else {
        val middle = rules[input.pair]!!
        val result = mutableMapOf(middle to 1L)
        result.combine(getInsight(PairWithDepth(Pair(input.pair.first, middle), input.depth - 1), rules))
        result.combine(getInsight(PairWithDepth(Pair(middle, input.pair.second), input.depth - 1), rules))
        result
    }
    cache[input] = r
    return r
}

fun MutableMap<Char, Long>.combine(m: Map<Char, Long>) {
    m.forEach { p -> this.compute(p.key) { _, i -> if (i == null) p.value else i + p.value } }
}

fun main() {
    val lines = Input.readFileLines("day14", "input.txt").filter { it.isNotBlank() }

    val template = lines.first()

    val rules = lines.drop(1).map { line -> line.split("->").map { it.trim() } }
        .associate { Pair(Pair(it[0][0], it[0][1]), it[1].first()) }

    println(template)
    println(rules)

    val charsCount = mutableMapOf<Char, Long>()
    template.forEach { c -> charsCount.compute(c) { _, i -> if (i == null) 1L else i + 1 } }

    val start = System.currentTimeMillis()
    for (i in 1 until template.length) {
        val p = Pair(template[i - 1], template[i])
        charsCount.combine(getInsight(PairWithDepth(p, 40), rules))
    }
    val end = System.currentTimeMillis()

    println(charsCount)
    println(charsCount.maxOf { it.value } - charsCount.minOf { it.value })

    println("time: ${end - start}")
}
