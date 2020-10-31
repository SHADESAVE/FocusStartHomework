package com.example.focusstarthomework.speedometer.ui.speedometer.utils

import android.view.animation.Interpolator
import kotlin.math.cos
import kotlin.math.pow

/*
    Ускорение в начале и в конце, в середине замедление
 */
class DecelerateAccelerateInterpolator : Interpolator {
    override fun getInterpolation(x: Float) = ((2.0f*x-1.0f).pow(3) + 2.0f*x+1.0f) / 4.0f
}