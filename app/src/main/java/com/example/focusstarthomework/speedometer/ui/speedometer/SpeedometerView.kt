package com.example.focusstarthomework.speedometer.ui.speedometer

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.example.focusstarthomework.R
import kotlinx.android.synthetic.main.speedometer_view.view.*

class SpeedometerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.speedometer_view, this)
        gaz_button.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    arrow.changeState(SpeedometerState.BUTTON_DOWN)
                }
                MotionEvent.ACTION_UP -> {
                    view.performClick()
                    arrow.changeState(SpeedometerState.BUTTON_UP)
                }
            }
            false
        }
        //{ arrow.rotateArrow() }
    }
}