package com.example.focusstarthomework.speedometer.ui.speedometer

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout

class ArkView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // основная переменная, определяющая размер всех элементов на экране
    private var radius = 0.0f
    private val borderWidth = 50f

    private val oval = RectF()
    private val innerOval = RectF()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        // Calculate the radius from the smaller of the width and height.
        radius = (width.coerceAtMost(height) / 2.0 * 0.8).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        oval[width / 2 - radius, height / 2 - radius, width / 2 + radius] = height / 2 + radius
        innerOval[width / 2 - radius / 2, height / 2 - radius / 2, width / 2 + radius / 2] =
            height / 2 + radius / 2

        canvas.apply {

            //circle background
            paint.style = Paint.Style.FILL
            paint.strokeWidth = borderWidth
            paint.color = Color.GRAY
            drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius + borderWidth, paint)

            //ark background
            paint.style = Paint.Style.FILL
            paint.color = Color.DKGRAY
            drawArc(oval, 50f, -280f, true, paint)

            //inner ark background
            paint.style = Paint.Style.FILL
            paint.color = Color.LTGRAY
            drawArc(innerOval, 50f, -280f, true, paint)

            //ark stroke
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = borderWidth
            paint.color = Color.LTGRAY
            drawArc(oval, 50f, -280f, false, paint)

            //central circle
            paint.style = Paint.Style.FILL
            paint.color = Color.BLACK
            drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius / 5, paint)


            //main numbers of speed
            paint.style = Paint.Style.FILL
            paint.color = Color.YELLOW
            paint.textAlign = Paint.Align.CENTER
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            paint.textSize = radius * 0.20f
            drawText("80", (width / 2).toFloat() - radius  / 1.5f, (height / 2.toFloat()) - radius / 5f, paint)
            drawText("160", (width / 2).toFloat(), (height / 2.toFloat()) - radius / 1.5f, paint)
            drawText("240", (width / 2).toFloat() + radius / 1.5f, (height / 2.toFloat()) - radius / 5f, paint)

            //numbers between main numbers
            paint.textSize = radius * 0.15f
            drawText("40", (width / 2).toFloat() - radius / 1.35f, (height / 2.toFloat()) + radius / 4f, paint)
            drawText("120", (width / 2).toFloat() - radius / 2.25f, (height / 2.toFloat()) - radius / 1.75f, paint)
            drawText("200", (width / 2).toFloat() + radius / 2.25f, (height / 2.toFloat()) - radius / 1.75f, paint)
            drawText("280", (width / 2).toFloat() + radius / 1.35f, (height / 2.toFloat()) + radius / 4f, paint)
        }
    }
}