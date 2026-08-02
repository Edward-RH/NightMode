package com.nightfilter.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import java.util.Calendar

class FilterOverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private var filterView: FilterOverlayView? = null
    private lateinit var prefs: PreferencesManager
    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            updateFilter()
            handler.postDelayed(this, 60000) // Actualizar cada minuto
        }
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        prefs = PreferencesManager(this)
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, createNotification())
        
        if (filterView == null) {
            filterView = FilterOverlayView(this)
            val params = WindowManager.LayoutParams().apply {
                type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                } else {
                    @Suppress("DEPRECATION")
                    WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY
                }
                format = PixelFormat.TRANSLUCENT
                flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                width = WindowManager.LayoutParams.MATCH_PARENT
                height = WindowManager.LayoutParams.MATCH_PARENT
                x = 0
                y = 0
            }
            windowManager.addView(filterView, params)
        }

        updateFilter()
        handler.post(updateRunnable)

        return START_STICKY
    }

    private fun updateFilter() {
        val isScheduled = prefs.isScheduledMode()
        
        if (isScheduled) {
            val now = Calendar.getInstance()
            val currentHour = now.get(Calendar.HOUR_OF_DAY)
            val currentMinute = now.get(Calendar.MINUTE)
            val currentMinutes = currentHour * 60 + currentMinute

            val startHour = prefs.getStartHour()
            val startMinute = prefs.getStartMinute()
            val startMinutes = startHour * 60 + startMinute

            val endHour = prefs.getEndHour()
            val endMinute = prefs.getEndMinute()
            val endMinutes = endHour * 60 + endMinute

            // Calcular intensidad progresiva
            val intensity = if (startMinutes <= endMinutes) {
                // Horario normal (ej: 20:00 a 06:00 no cruza medianoche)
                when {
                    currentMinutes in startMinutes..endMinutes -> {
                        val range = endMinutes - startMinutes
                        val elapsed = currentMinutes - startMinutes
                        (elapsed * 100) / range
                    }
                    else -> 0
                }
            } else {
                // Horario que cruza medianoche (ej: 22:00 a 06:00)
                when {
                    currentMinutes >= startMinutes || currentMinutes <= endMinutes -> {
                        val range = (1440 - startMinutes) + endMinutes
                        val elapsed = if (currentMinutes >= startMinutes) {
                            currentMinutes - startMinutes
                        } else {
                            (1440 - startMinutes) + currentMinutes
                        }
                        (elapsed * 100) / range
                    }
                    else -> 0
                }
            }

            filterView?.setIntensity(intensity)
        } else {
            filterView?.setIntensity(100) // Modo manual a máxima intensidad del slider
        }

        filterView?.apply {
            setSaturation(prefs.getSaturation())
            setTemperature(prefs.getTemperature())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Filtro de Noche",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Filtro de Noche Activo")
            .setContentText("El filtro de pantalla está funcionando")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setOngoing(true)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateRunnable)
        filterView?.let { windowManager.removeView(it) }
        filterView = null
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        private const val CHANNEL_ID = "night_filter_channel"
        private const val NOTIFICATION_ID = 1
    }
}
