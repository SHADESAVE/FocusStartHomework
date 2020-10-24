package com.example.focusstarthomework.kotlin.koans

import android.widget.AdapterView


fun simpleFun(): String = "It's simple func"

fun namedArguments() { defaultArguments(age = 10) }

fun defaultArguments(name: String = "", age: Int? = null) {}

fun lambda(list: List<Int>) = list.forEach { println(it) }

fun strings(): String = """\d{2} ${simpleFun()} \d{4}"""

data class Person(val name: String, val age: Int? = null)
fun getPerson(name: String, age: Int?) = Person(name, age)

fun nullable(person: Person?) = person?.age?.let { person.copy(age = 1 + it) }

fun List<Int>.extensionFun() = this.forEach { println(it) }

object comparator : Comparator<Int> {
    override fun compare(x: Int, y: Int) = y - x
}

fun SAMconversions(clickListener: (Person) -> Unit) {}
fun printPersonName() = SAMconversions { person -> print(person.name) }

fun extensionsOnCollections(list: List<Int>) { list.extensionFun() }