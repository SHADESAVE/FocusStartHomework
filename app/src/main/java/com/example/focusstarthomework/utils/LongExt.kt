package com.example.focusstarthomework.utils

private const val TWO_DIGIT_NUMBER = 10

fun Int.toTimeFormatString(): String {
    val seconds = (this % 60).twoDigitNumberToString()
    val minutes = (this % 3600 / 60).twoDigitNumberToString()
    val hours = (this / 3600).twoDigitNumberToString()
    return "$hours:$minutes:$seconds"
}

private fun Int.twoDigitNumberToString(): String =
    if (this < TWO_DIGIT_NUMBER) "0$this" else this.toString()