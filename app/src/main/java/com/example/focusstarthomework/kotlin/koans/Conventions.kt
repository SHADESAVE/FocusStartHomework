package com.example.focusstarthomework.kotlin.koans

data class Box(val width: Int, val height: Int) : Comparable<Int> {
    override fun compareTo(other: Int): Int = this.height - other
    operator fun contains(value: Int): Boolean = this.width < value && value > 0
    operator fun rangeTo(box: Box): IntRange = this.width..box.width
    operator fun minus(box: Box) = Box(this.width - box.width, this.height - box.height)
    operator fun invoke() { print(this) }
}

fun boxComparsion(box: Box, criticalHeight: Int) = box < criticalHeight

fun boxWidthContains() {
    10 in Box(30, 15)
}

fun boxWidthRangeTo() {
    print(25 in Box(20, 40)..Box(30, 30))
}

fun boxLoop(box1: Box, box2: Box) {
    for (width in box1..box2) {
        print(width)
    }
}

fun boxMinusOperatorOverloading(box1: Box, box2: Box) {
    print(box1 - box2)
}

fun boxInvoke(box1: Box) { box1() }