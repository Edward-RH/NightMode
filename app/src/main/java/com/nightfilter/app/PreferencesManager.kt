package com.nightfilter.app

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "night_filter_prefs",
        Context.MODE_PRIVATE
    )

    // Filter state
    fun isFilterEnabled(): Boolean = prefs.getBoolean("filter_enabled", false)
    fun setFilterEnabled(enabled: Boolean) = prefs.edit().putBoolean("filter_enabled", enabled).apply()

    // Mode: scheduled vs manual
    fun isScheduledMode(): Boolean = prefs.getBoolean("scheduled_mode", false)
    fun setScheduledMode(scheduled: Boolean) = prefs.edit().putBoolean("scheduled_mode", scheduled).apply()

    // Saturation (0-100%)
    fun getSaturation(): Int = prefs.getInt("saturation", 50)
    fun setSaturation(value: Int) = prefs.edit().putInt("saturation", value).apply()

    // Temperature (0-7000K, but we add 3000K in the view)
    fun getTemperature(): Int = prefs.getInt("temperature", 3500)
    fun setTemperature(value: Int) = prefs.edit().putInt("temperature", value).apply()

    // Schedule times
    fun getStartHour(): Int = prefs.getInt("start_hour", 20)
    fun setStartHour(hour: Int) = prefs.edit().putInt("start_hour", hour).apply()

    fun getStartMinute(): Int = prefs.getInt("start_minute", 0)
    fun setStartMinute(minute: Int) = prefs.edit().putInt("start_minute", minute).apply()

    fun getEndHour(): Int = prefs.getInt("end_hour", 6)
    fun setEndHour(hour: Int) = prefs.edit().putInt("end_hour", hour).apply()

    fun getEndMinute(): Int = prefs.getInt("end_minute", 0)
    fun setEndMinute(minute: Int) = prefs.edit().putInt("end_minute", minute).apply()
}
