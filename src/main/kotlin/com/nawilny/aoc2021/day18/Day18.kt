package com.nawilny.aoc2021.day18

import com.nawilny.aoc2021.common.Input
import kotlin.math.ceil
import kotlin.math.floor

private data class Element(var left: Element?, var right: Element?, var value: Int?) {

    fun isPair() = value == null

    override fun toString() = if (isPair()) "[$left,$right]" else value.toString()

    fun addToLeft(i: Int) {
        if (isPair()) {
            left!!.addToLeft(i)
        } else {
            value = value!! + i
        }
    }

    fun addToRight(i: Int) {
        if (isPair()) {
            right!!.addToRight(i)
        } else {
            value = value!! + i
        }
    }

    fun explode(depth: Int): Pair<Int, Int>? {
        if (!isPair()) {
            return null
        }
        if (depth < 3) {
            val l = left!!.explode(depth + 1)
            if (l !== null) {
                right!!.addToLeft(l.second)
                return Pair(l.first, 0)
            }
            val r = right!!.explode(depth + 1)
            if (r !== null) {
                left!!.addToRight(r.first)
                return Pair(0, r.second)
            }
        } else {
            if (left!!.isPair()) {
                val l = left!!.left!!.value!!
                val r = left!!.right!!.value!!
                left = Element(null, null, 0)
                right!!.addToLeft(r)
                return Pair(l, 0)
            }
            if (right!!.isPair()) {
                val l = right!!.left!!.value!!
                val r = right!!.right!!.value!!
                right = Element(null, null, 0)
                left!!.addToRight(l)
                return Pair(0, r)
            }
        }
        return null
    }

    fun split(): Boolean {
        if (left!!.isPair()) {
            val r = left!!.split()
            if (r) {
                return true
            }
        } else if (left!!.value!! >= 10) {
            left = performSplit(left!!.value!!)
            return true
        }
        if (right!!.isPair()) {
            val r = right!!.split()
            if (r) {
                return true
            }
        } else if (right!!.value!! >= 10) {
            right = performSplit(right!!.value!!)
            return true
        }
        return false
    }

    fun performSplit(v: Int): Element {
        val l = floor(v.toDouble() / 2).toInt()
        val r = ceil(v.toDouble() / 2).toInt()
        return Element(Element(null, null, l), Element(null, null, r), null)
    }

    fun magnitude(): Int {
        return if (isPair()) {
            (3 * left!!.magnitude()) + (2 * right!!.magnitude())
        } else {
            this.value!!
        }
    }

    fun duplicate(): Element {
        return if (isPair()) {
            Element(left!!.duplicate(), right!!.duplicate(), null)
        } else {
            Element(null, null, value)
        }
    }

}

private fun parse(input: String, position: Int): Pair<Element, Int> {
    val c = input[position]
    return if (c.isDigit()) {
        Pair(Element(null, null, c.digitToInt()), position + 1)
    } else if (c == '[') {
        val left = parse(input, position + 1)
        val right = parse(input, left.second + 1)
        Pair(Element(left.first, right.first, null), right.second + 1)
    } else {
        error("Unsupported char '$c'")
    }
}

fun explode(input: String, expected: String) {
    println("----------------")
    val root = parse(input, 0).first
    println(root)
    println(root.explode(0))
    println(root)
    if (root.toString() == expected) {
        println("OK")
    } else {
        println("NOT - expected $expected")
    }
}

private fun reduce(element: Element) {
    var done = false
    while (!done) {
        val e = element.explode(0)
        if (e != null) {
            continue
        }
        val s = element.split()
        if (s) {
            continue
        }
        done = true
    }
}

private fun add(e1: Element?, e2: Element): Element {
    if (e1 == null) {
        return e2
    }
    val e = Element(e1, e2, null)
    reduce(e)
    return e
}

fun main() {
    val lines = Input.readFileLines("day18", "input.txt").filter { it.isNotBlank() }.map { parse(it, 0).first }

//    explode("[[[[[9,8],1],2],3],4]", "[[[[0,9],2],3],4]")
//    explode("[7,[6,[5,[4,[3,2]]]]]", "[7,[6,[5,[7,0]]]]")
//    explode("[[6,[5,[4,[3,2]]]],1]", "[[6,[5,[7,0]]],3]")
//    explode("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")
//    explode("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[7,0]]]]")
//
//    val i = parse("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]", 0).first
//    println(i)
//    reduce(i)
//    println(i)

    // PART 1
//    val result = lines.fold(null as Element?) { acc, e -> add(acc, e) }
//    println(result)
//    println(result!!.magnitude())

    // PART 2

    println(lines)
    val result2 = lines.flatMap { l -> lines.map { Pair(l.duplicate(), it.duplicate()) } }
        .filter { it.first != it.second }
        .map { add(it.first, it.second) }
        .map { it.magnitude() }
        .maxOrNull()
    println(result2)
}
