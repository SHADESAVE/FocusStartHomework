package com.example.focusstarthomework.kotlin.koans

// Extension function literals
fun sum() {
    val sum: Int.(Int) -> Int = { other -> plus(other) }
    print(sum(10, 20)) // 30
}

// String & map builder
fun <K, V> buildMutableMap(build: HashMap<K, V>.() -> Unit): Map<K, V> {
    val map = HashMap<K, V>()
    map.build()
    return map
}

fun usage(): Map<Int, String> {
    return buildMutableMap {
        put(0, "0")
        for (i in 1..10) {
            put(i, "$i")
        }
    }
}


// apply
fun apply() {
    val list = mutableListOf<Int>()
    val value = 2
    value.apply {
        list.add(this*2)
        list.add(this*4)
    }
}