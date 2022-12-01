package com.nawilny.aoc2021.day12

import com.nawilny.aoc2021.common.Input

private data class Node(val name: String) {
    val neighbors = mutableSetOf<Node>()
}

private fun calculatePaths(n: Node, visitedNodes: Set<String>): Int {
    if (n.name == "end") {
        return 1
    }
    if (visitedNodes.contains(n.name)) {
        return 0
    }
    val nextVisited = if (n.name.lowercase() == n.name) visitedNodes.plus(n.name) else visitedNodes
    return n.neighbors.sumOf { calculatePaths(it, nextVisited) }
}

private fun calculatePaths2(n: Node, visitedNodes: Set<String>, visitedTwice: Boolean): Int {
    if (n.name == "end") {
        return 1
    }
    if (visitedNodes.contains(n.name)) {
        return if (visitedTwice || n.name == "start") {
            0
        } else {
            n.neighbors.sumOf { calculatePaths2(it, visitedNodes, true) }
        }
    }
    val nextVisited = if (n.name.lowercase() == n.name) visitedNodes.plus(n.name) else visitedNodes
    return n.neighbors.sumOf { calculatePaths2(it, nextVisited, visitedTwice) }
}

fun main() {
    val lines = Input.readFileLines("day12", "input.txt").filter { it.isNotBlank() }.map { it.split("-") }

    val nodes = mutableMapOf<String, Node>()
    lines.forEach {
        nodes.putIfAbsent(it[0], Node(it[0]))
        nodes.putIfAbsent(it[1], Node(it[1]))
        nodes[it[0]]!!.neighbors.add(nodes[it[1]]!!)
        nodes[it[1]]!!.neighbors.add(nodes[it[0]]!!)
    }

    println(calculatePaths2(nodes["start"]!!, setOf(), false))
}
