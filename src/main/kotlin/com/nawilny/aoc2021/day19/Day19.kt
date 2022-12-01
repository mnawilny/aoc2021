package com.nawilny.aoc2021.day19

import com.nawilny.aoc2021.common.Input
import kotlin.math.abs

private data class Point3D(val x: Int, val y: Int, val z: Int) {
    override fun toString() = "$x,$y,$z"
}

private data class Rotation(val r1: Point3D, val r2: Point3D, val r3: Point3D) {
    fun rotate(p: Point3D): Point3D {
        val x = r1.x * p.x + r1.y * p.y + r1.z * p.z
        val y = r2.x * p.x + r2.y * p.y + r2.z * p.z
        val z = r3.x * p.x + r3.y * p.y + r3.z * p.z
        return Point3D(x, y, z)
    }
}

private data class Scanner(val number: Int, val beacons: Set<Point3D>) {
    fun print() {
        println(" --- $number ---")
        beacons.forEach { println(it) }
    }
}

private fun parseInput(lines: List<String>): List<Scanner> {
    val scanners = mutableListOf<Scanner>()
    var scannerNo = 0
    var beacons = mutableSetOf<Point3D>()
    lines.forEach { line ->
        if (line.startsWith("---")) {
            if (beacons.isNotEmpty()) {
                scanners.add(Scanner(scannerNo, beacons))
            }
            scannerNo = line.split(" ")[2].toInt()
            beacons = mutableSetOf()
        } else {
            val parts = line.split(",").map { it.trim().toInt() }
            beacons.add(Point3D(parts[0], parts[1], parts[2]))
        }
    }
    scanners.add(Scanner(scannerNo, beacons))
    return scanners
}

private fun move(p: Point3D, t: Point3D) = Point3D(p.x + t.x, p.y + t.y, p.z + t.z)

private fun findCommon(
    beacons1: Set<Point3D>,
    beacons2: Set<Point3D>,
    rotations: List<Rotation>
): Pair<Rotation, Point3D>? {
    beacons1.forEach { p1 ->
        rotations.forEach { r ->
            val rotated = beacons2.map { r.rotate(it) }
            rotated.forEach { p2 ->
                val t = Point3D(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z)
                val translated = rotated.map { move(it, t) }
                val common = translated.count { beacons1.contains(it) }
                if (common >= 12) {
                    return Pair(r, t)
                }
            }
        }
    }
    return null
}

fun main() {
    val lines = Input.readFileLines("day19", "input.txt").filter { it.isNotBlank() }
    val scanners = parseInput(lines)
//    scanners.forEach { it.print() }

    val rotations = mutableListOf<Rotation>()
    // x
    rotations.add(Rotation(Point3D(1, 0, 0), Point3D(0, 1, 0), Point3D(0, 0, 1)))
    rotations.add(Rotation(Point3D(1, 0, 0), Point3D(0, 0, 1), Point3D(0, -1, 0)))
    rotations.add(Rotation(Point3D(1, 0, 0), Point3D(0, -1, 0), Point3D(0, 0, -1)))
    rotations.add(Rotation(Point3D(1, 0, 0), Point3D(0, 0, -1), Point3D(0, 1, 0)))
    // -x
    rotations.add(Rotation(Point3D(-1, 0, 0), Point3D(0, 1, 0), Point3D(0, 0, -1)))
    rotations.add(Rotation(Point3D(-1, 0, 0), Point3D(0, 0, -1), Point3D(0, -1, 0)))
    rotations.add(Rotation(Point3D(-1, 0, 0), Point3D(0, -1, 0), Point3D(0, 0, 1)))
    rotations.add(Rotation(Point3D(-1, 0, 0), Point3D(0, 0, 1), Point3D(0, 1, 0)))
    // y
    rotations.add(Rotation(Point3D(0, -1, 0), Point3D(1, 0, 0), Point3D(0, 0, 1)))
    rotations.add(Rotation(Point3D(0, 0, -1), Point3D(1, 0, 0), Point3D(0, -1, 0)))
    rotations.add(Rotation(Point3D(0, 1, 0), Point3D(1, 0, 0), Point3D(0, 0, -1)))
    rotations.add(Rotation(Point3D(0, 0, 1), Point3D(1, 0, 0), Point3D(0, 1, 0)))
    // -y
    rotations.add(Rotation(Point3D(0, 1, 0), Point3D(-1, 0, 0), Point3D(0, 0, 1)))
    rotations.add(Rotation(Point3D(0, 0, -1), Point3D(-1, 0, 0), Point3D(0, 1, 0)))
    rotations.add(Rotation(Point3D(0, -1, 0), Point3D(-1, 0, 0), Point3D(0, 0, -1)))
    rotations.add(Rotation(Point3D(0, 0, 1), Point3D(-1, 0, 0), Point3D(0, -1, 0)))
    // z
    rotations.add(Rotation(Point3D(0, 0, -1), Point3D(0, 1, 0), Point3D(1, 0, 0)))
    rotations.add(Rotation(Point3D(0, 1, 0), Point3D(0, 0, 1), Point3D(1, 0, 0)))
    rotations.add(Rotation(Point3D(0, 0, 1), Point3D(0, -1, 0), Point3D(1, 0, 0)))
    rotations.add(Rotation(Point3D(0, -1, 0), Point3D(0, 0, -1), Point3D(1, 0, 0)))
    // -z
    rotations.add(Rotation(Point3D(0, 0, 1), Point3D(0, 1, 0), Point3D(-1, 0, 0)))
    rotations.add(Rotation(Point3D(0, 1, 0), Point3D(0, 0, -1), Point3D(-1, 0, 0)))
    rotations.add(Rotation(Point3D(0, 0, -1), Point3D(0, -1, 0), Point3D(-1, 0, 0)))
    rotations.add(Rotation(Point3D(0, -1, 0), Point3D(0, 0, 1), Point3D(-1, 0, 0)))

//    val p = Point3D(1, 2, 3)
//    rotations.forEach { println(it.rotate(p)) }

    val finalMap = scanners.first().beacons.toMutableSet()
    val translations = mutableListOf<Point3D>()

    val remaining = scanners.drop(1).toMutableList()
    while (remaining.isNotEmpty()) {
        var toRemove: Scanner? = null
        for (s in remaining) {
            val common = findCommon(finalMap, s.beacons, rotations)
            if (common != null) {
                translations.add(common.second)
                s.beacons.map { common.first.rotate(it) }.map { move(it, common.second) }.forEach { finalMap.add(it) }
                toRemove = s
                continue
            }
        }
        if (toRemove != null) {
            remaining.remove(toRemove)
        } else {
            error("Cannot find")
        }
    }

    println(finalMap)
    println(finalMap.count())
    println(translations)
    val max = translations.flatMap { t -> translations.map { Pair(t, it) } }.map {
        abs(it.first.x - it.second.x) + abs(it.first.y - it.second.y) + abs(it.first.z - it.second.z)
    }.maxOrNull()
    println(max)
}
