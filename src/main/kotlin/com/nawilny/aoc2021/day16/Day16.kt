package com.nawilny.aoc2021.day16

import com.nawilny.aoc2021.common.Input

private val HexMap = mapOf(
    '0' to "0000",
    '1' to "0001",
    '2' to "0010",
    '3' to "0011",
    '4' to "0100",
    '5' to "0101",
    '6' to "0110",
    '7' to "0111",
    '8' to "1000",
    '9' to "1001",
    'A' to "1010",
    'B' to "1011",
    'C' to "1100",
    'D' to "1101",
    'E' to "1110",
    'F' to "1111",
)

private sealed class Packet(val version: Int, val type: Int) {
    abstract fun sumVersions(): Int
    abstract fun evaluate(): Long
}

private class TypeLiteralPacket(version: Int, type: Int, val value: Long) : Packet(version, type) {
    override fun sumVersions(): Int = version
    override fun evaluate() = value
    override fun toString() = "TypeLiteralPacket(version=$version, type=$type, value=$value)"
}

private class OperatorPacket(version: Int, type: Int, val subpackets: List<Packet>) : Packet(version, type) {
    override fun sumVersions(): Int = version + subpackets.sumOf { it.sumVersions() }
    override fun evaluate(): Long {
        val values = subpackets.map { it.evaluate() }
        return when (type) {
            0 -> values.sum()
            1 -> values.fold(1L) { acc, l -> acc * l }
            2 -> values.minOf { it }
            3 -> values.maxOf { it }
            5 -> if (values[0] > values[1]) 1 else 0
            6 -> if (values[0] < values[1]) 1 else 0
            7 -> if (values[0] == values[1]) 1 else 0
            else -> error("unknown type $type")
        }
    }

    override fun toString() = "OperatorPacket(version=$version, type=$type, subpackets=$subpackets)"
}

private class BinaryInput(val binary: String) {
    var position = 0
    fun read(i: Int) = (1..i).fold("") { s, _ -> s + binary[position++] }
    fun readInt(i: Int) = read(i).toInt(2)
    fun isEnd() = position >= binary.length || binary.substring(position).all { it == '0' }
    fun subInput(i: Int): BinaryInput {
        val sub = BinaryInput(binary.substring(position, position + i))
        position += i
        return sub
    }
}

private fun parse(input: BinaryInput): Packet {
    val version = input.readInt(3)
    val type = input.readInt(3)
    return if (type == 4) {
        var binaryValue = ""
        while (input.readInt(1) == 1) {
            binaryValue += input.read(4)
        }
        binaryValue += input.read(4)
        TypeLiteralPacket(version, type, binaryValue.toLong(2))
    } else {
        val lengthType = input.readInt(1)
        val subpackets = if (lengthType == 0) {
            val totalLength = input.readInt(15)
            val subInput = input.subInput(totalLength)
            val l = mutableListOf<Packet>()
            while (!subInput.isEnd()) {
                l.add(parse(subInput))
            }
            l
        } else {
            val subpacketsNumber = input.readInt(11)
            (1..subpacketsNumber).fold(listOf()) { acc, _ -> acc.plus(parse(input)) }
        }
        OperatorPacket(version, type, subpackets)
    }
}

fun main() {
    val input = Input.readFileLines("day16", "input.txt").first()
//    val input = "D2FE28"
//    val input = "38006F45291200"
//    val input = "EE00D40C823060"
//    val input = "8A004A801A8002F478" // 16
//    val input = "620080001611562C8802118E34" // 12
//    val input = "C0015000016115A2E0802F182340" // 23
//    val input = "A0016C880162017C3686B18A3D4780" // 31

    println(input)

    val binary = input.map { HexMap[it] }.joinToString("")
    println(binary)
    val binaryInput = BinaryInput(binary)

    val packet = parse(binaryInput)
    println(packet)
    println(binaryInput.isEnd())
    println(packet.sumVersions())
    println(packet.evaluate())
}
