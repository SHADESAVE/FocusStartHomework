package com.example.focusstarthomework.utils

fun String.toListOfWords() =
    this.split("\n", ",", " ", ".", "—", ":", "!", "?")
        .filter { it.isNotEmpty() }