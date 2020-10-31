package com.example.focusstarthomework.speedometer.ui.speedometer

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import com.example.focusstarthomework.speedometer.ui.speedometer.utils.DecelerateAccelerateInterpolator
import kotlin.math.cos
import kotlin.math.sin

class ArrowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private companion object {
        const val START_ANGLE = 0f
        const val END_ANGLE = 280f
        const val MAIN_DURATION = 5000L
        const val SUPER_STATE = "super_state"
        const val SPEEDOMETER_STATE_KEY = "state"
        const val CURRENT_ANGLE_STATE_KEY = "currentAngle"
        const val FINAL_ANGLE_STATE_KEY = "finalAngle"
        const val ARROW_COLOR_STATE_KEY = "arrowColor"
    }

    private var state = SpeedometerState.BUTTON_UP

    private var currentAngle = START_ANGLE
    private var finalAngle = END_ANGLE
    private var arrowColor = Color.BLUE

    private var mainAnimator: AnimatorSet? = null

    private var radius = 0.0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        // Calculate the radius from the smaller of the width and height.
        radius = (width.coerceAtMost(height) / 2.0 * 0.8).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.FILL
        paint.strokeWidth = 20f
        paint.color = arrowColor

        canvas.drawLine(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (width / 2).toFloat() - radius * cos(70f),
            (height / 2).toFloat() + radius * sin(70f),
            paint
        )
    }

    override fun onSaveInstanceState(): Parcelable? =
        Bundle().apply {
            putInt(SPEEDOMETER_STATE_KEY, state.ordinal)
            putFloat(CURRENT_ANGLE_STATE_KEY, currentAngle)
            putFloat(FINAL_ANGLE_STATE_KEY, finalAngle)
            putInt(ARROW_COLOR_STATE_KEY, arrowColor)
            putParcelable(SUPER_STATE, super.onSaveInstanceState())
        }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var superState = state

        if (state is Bundle) {
            currentAngle = state.getFloat(CURRENT_ANGLE_STATE_KEY)
            finalAngle = state.getFloat(FINAL_ANGLE_STATE_KEY)
            arrowColor = state.getInt(ARROW_COLOR_STATE_KEY)
            /*
                changeState(SpeedometerState.getStateFromOrdinal(state.getInt(SPEEDOMETER_STATE_KEY)))
                При смене ориентации экрана, считаем, что кнопка отжата
            */
            changeState(SpeedometerState.BUTTON_UP)
            superState = state.getParcelable(SUPER_STATE)
        }

        super.onRestoreInstanceState(superState)
    }

    fun changeState(state: SpeedometerState) {
        this.state = state
        when(state) {
            SpeedometerState.BUTTON_DOWN -> {
                finalAngle = END_ANGLE
                initializeMainAnimator()
            }
            SpeedometerState.BUTTON_UP -> {
                finalAngle = START_ANGLE
                initializeMainAnimator()
            }
        }
    }

    private fun initializeMainAnimator() {
        mainAnimator?.cancel()
        mainAnimator = AnimatorSet().apply {
            play(initializeRotateAnimator()).with(initializeColorAnimator())
            start()
        }
    }

    private fun initializeRotateAnimator(): ObjectAnimator =
        ObjectAnimator.ofFloat(this, ROTATION, currentAngle, finalAngle).apply {
            interpolator =
                DecelerateAccelerateInterpolator()
            addUpdateListener { currentAngle = it.animatedValue as Float }
            duration = MAIN_DURATION
        }

    private fun initializeColorAnimator(): ValueAnimator =
        ValueAnimator().apply {
            if (finalAngle == END_ANGLE)
                setIntValues(arrowColor, Color.RED)
            else
                setIntValues(arrowColor, Color.BLUE)
            setEvaluator(ArgbEvaluator())
            addUpdateListener { _ ->
                (this.animatedValue as? Int)?.let { arrowColor = it }
                invalidate()
            }
            duration = MAIN_DURATION
        }
}