package com.nightfilter.app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class FilterOverlayView(context: Context) : View(context) {

    private var saturation = 100
    private var temperature = 6500
    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val filterColor = calculateColorFromTemperature(temperature)
        val alpha = (saturation * 255) / 100
        val finalColor = Color.argb(
            alpha,
            Color.red(filterColor),
            Color.green(filterColor),
            Color.blue(filterColor)
        )

        paint.color = finalColor
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        postInvalidateDelayed(1000)
    }

    private fun calculateColorFromTemperature(kelvin: Int): Int {
        val temp = kelvin / 100
        val red = 255
        val green = ((255.0 / 1000.0) * (temp - 1000)).toInt().coerceIn(0, 255)
        val blue = 0

        return Color.rgb(red, green, blue)
    }

    fun setSaturation(value: Int) {
        saturation = value
        invalidate()
    }

    fun setTemperature(value: Int) {
        temperature = value + 3000
        invalidate()
    }
}
