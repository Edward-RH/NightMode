package com.nightfilter.app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class FilterOverlayView(context: Context) : View(context) {

    private var saturation = 100 // 0-100%
    private var temperature = 6500 // Kelvin
    private var intensity = 100 // 0-100%
    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Calcular el color del filtro basado en temperatura
        val filterColor = calculateColorFromTemperature(temperature)
        
        // Aplicar saturación y intensidad
        val alpha = (intensity * 255) / 100
        val finalColor = Color.argb(
            alpha,
            Color.red(filterColor),
            Color.green(filterColor),
            Color.blue(filterColor)
        )

        paint.color = finalColor
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        // Redibujar cada 1000ms para actualizar
        postInvalidateDelayed(1000)
    }

    private fun calculateColorFromTemperature(kelvin: Int): Int {
        // Convertir temperatura Kelvin a RGB
        // Valores típicos: 3000K (cálido/naranja) a 9000K (frío/azul)
        
        val temp = kelvin / 100
        val red: Int
        val green: Int
        val blue: Int

        red = when {
            temp <= 66 -> 255
            else -> {
                val r = 329.698727446 * Math.pow((temp - 60).toDouble(), -0.1332047592)
                r.toInt().coerceIn(0, 255)
            }
        }

        green = when {
            temp <= 66 -> {
                val g = 99.4708025861 * Math.log(temp.toDouble()) - 161.1195681661
                g.toInt().coerceIn(0, 255)
            }
            else -> {
                val g = 288.1221695283 * Math.pow((temp - 60).toDouble(), -0.0755148492)
                g.toInt().coerceIn(0, 255)
            }
        }

        blue = when {
            temp >= 66 -> 255
            temp <= 19 -> 0
            else -> {
                val b = 138.5177312231 * Math.log((temp - 10).toDouble()) - 305.0447927307
                b.toInt().coerceIn(0, 255)
            }
        }

        return Color.rgb(red, green, blue)
    }

    fun setSaturation(value: Int) {
        this.saturation = value
        invalidate()
    }

    fun setTemperature(value: Int) {
        this.temperature = value + 3000 // Rango: 3000K - 10000K
        invalidate()
    }

    fun setIntensity(value: Int) {
        this.intensity = value.coerceIn(0, 100)
        invalidate()
    }
}
