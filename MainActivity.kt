package com.nightfilter.app

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var filterToggle: Switch
    private lateinit var modeSwitch: Switch
    private lateinit var saturacionSlider: SeekBar
    private lateinit var temperaturaSlider: SeekBar
    private lateinit var horarioStartText: TextView
    private lateinit var horarioEndText: TextView
    private lateinit var saturacionValue: TextView
    private lateinit var temperaturaValue: TextView
    private lateinit var prefs: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = PreferencesManager(this)
        initViews()
        setupPermissions()
        setupListeners()
        updateUI()
    }

    private fun initViews() {
        filterToggle = findViewById(R.id.filterToggle)
        modeSwitch = findViewById(R.id.modeSwitch)
        saturacionSlider = findViewById(R.id.saturacionSlider)
        temperaturaSlider = findViewById(R.id.temperaturaSlider)
        horarioStartText = findViewById(R.id.horarioStartText)
        horarioEndText = findViewById(R.id.horarioEndText)
        saturacionValue = findViewById(R.id.saturacionValue)
        temperaturaValue = findViewById(R.id.temperaturaValue)
    }

    private fun setupPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }
        }
    }

    private fun setupListeners() {
        filterToggle.setOnCheckedChangeListener { _, isChecked ->
            prefs.setFilterEnabled(isChecked)
            if (isChecked) {
                startFilterService()
            } else {
                stopFilterService()
            }
            updateUI()
        }

        modeSwitch.setOnCheckedChangeListener { _, isScheduled ->
            prefs.setScheduledMode(isScheduled)
            updateUI()
        }

        saturacionSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                prefs.setSaturation(progress)
                saturacionValue.text = "$progress%"
                updateFilter()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        temperaturaSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                prefs.setTemperature(progress)
                temperaturaValue.text = "${progress}K"
                updateFilter()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateUI() {
        val isEnabled = prefs.isFilterEnabled()
        val isScheduled = prefs.isScheduledMode()

        filterToggle.isChecked = isEnabled
        modeSwitch.isChecked = isScheduled

        saturacionSlider.progress = prefs.getSaturation()
        temperaturaSlider.progress = prefs.getTemperature()

        saturacionValue.text = "${prefs.getSaturation()}%"
        temperaturaValue.text = "${prefs.getTemperature()}K"

        // Mostrar horarios
        horarioStartText.text = "Inicio: ${prefs.getStartHour()}:${String.format("%02d", prefs.getStartMinute())}"
        horarioEndText.text = "Fin: ${prefs.getEndHour()}:${String.format("%02d", prefs.getEndMinute())}"
    }

    private fun updateFilter() {
        if (prefs.isFilterEnabled()) {
            stopFilterService()
            startFilterService()
        }
    }

    private fun startFilterService() {
        val intent = Intent(this, FilterOverlayService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    private fun stopFilterService() {
        stopService(Intent(this, FilterOverlayService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!prefs.isFilterEnabled()) {
            stopFilterService()
        }
    }
}
