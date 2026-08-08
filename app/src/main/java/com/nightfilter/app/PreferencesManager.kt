package com.nightfilter.app

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "night_filter_prefs",
        Context.MODE_PRIVATE
    )

    fun isFilterEnabled(): Boolean = prefs.getBoolean("filter_enabled", false)
    fun setFilterEnabled(enabled: Boolean) = prefs.edit().putBoolean("filter_enabled", enabled).apply()

    fun isScheduledMode(): Boolean = prefs.getBoolean("scheduled_mode", false)
    fun setScheduledMode(scheduled: Boolean) = prefs.edit().putBoolean("scheduled_mode", scheduled).apply()

    fun getSaturation(): Int = prefs.getInt("saturation", 50)
    fun setSaturation(value: Int) = prefs.edit().putInt("saturation", value).apply()

    fun getTemperature(): Int = prefs.getInt("temperature", 3500)
    fun setTemperature(value: Int) = prefs.edit().putInt("temperature", value).apply()

    fun getStartHour(): Int = prefs.getInt("start_hour", 20)
    fun getStartMinute(): Int = prefs.getInt("start_minute", 0)
    fun getEndHour(): Int = prefs.getInt("end_hour", 6)
    fun getEndMinute(): Int = prefs.getInt("end_minute", 0)
}
