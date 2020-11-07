package com.example.focusstarthomework.utils

private const val TWO_DIGIT_NUMBER = 10

fun Long.toTimeFormatString(): String {
    val seconds = ((this / 1000) % 60).twoDigitNumberToString()
    val minutes = ((this / 1000) / 60).twoDigitNumberToString()
    val hours = ((this / 1000) / 360).twoDigitNumberToString()
    return "$hours:$minutes:$seconds"
}

private fun Long.twoDigitNumberToString(): String =
    if (this < TWO_DIGIT_NUMBER) "0$this" else this.toString()