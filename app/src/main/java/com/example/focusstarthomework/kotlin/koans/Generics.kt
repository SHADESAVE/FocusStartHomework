package com.example.focusstarthomework.kotlin.koans

fun generics() {
    val list = listOf("k", "o", "t", "l", "i", "n")
    print(list.getEvenElements()) // [k, t, i]
}

fun <T> List<T>.getEvenElements(): List<T> = this.filterIndexed { index, t ->
    index % 2 == 0
}