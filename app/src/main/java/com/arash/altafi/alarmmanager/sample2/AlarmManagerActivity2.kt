package com.arash.altafi.alarmmanager.sample2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.arash.altafi.alarmmanager.R
import com.google.android.material.button.MaterialButton

class AlarmManagerActivity2 : AppCompatActivity() {

    private lateinit var btnAlarm: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_manager2)

        init()
    }

    private fun init() {
        btnAlarm = findViewById(R.id.btn_alarm_2)
        btnAlarm.setOnClickListener {
            PollReceiver.scheduleAlarms(this)
            Toast.makeText(this, "Alarms scheduled!", Toast.LENGTH_LONG).show()
        }
    }
}