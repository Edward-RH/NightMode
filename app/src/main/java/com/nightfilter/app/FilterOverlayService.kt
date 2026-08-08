package com.nightfilter.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.WindowManager
import androidx.core.app.NotificationCompat

class FilterOverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private var filterView: FilterOverlayView? = null
    private lateinit var prefs: PreferencesManager

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        prefs = PreferencesManager(this)
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, createNotification())
        
        if (filterView == null) {
            filterView = FilterOverlayView(this)
            val view = filterView
            if (view != null) {
                val params = WindowManager.LayoutParams()
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                params.format = PixelFormat.TRANSLUCENT
                params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = WindowManager.LayoutParams.MATCH_PARENT
                windowManager.addView(view, params)
            }
        }

        if (filterView != null) {
            filterView!!.setSaturation(prefs.getSaturation())
            filterView!!.setTemperature(prefs.getTemperature())
        }

        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("night_filter", "Filtro", NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, "night_filter")
            .setContentTitle("Filtro Activo")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setOngoing(true)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (filterView != null) {
            windowManager.removeView(filterView)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
