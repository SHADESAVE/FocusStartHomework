package com.example.focusstarthomework.speedometer.ui.speedometer

enum class SpeedometerState{
    BUTTON_UP,
    BUTTON_DOWN;

    companion object {
        fun getStateFromOrdinal(ordinal: Int) = values()[ordinal]
    }
}