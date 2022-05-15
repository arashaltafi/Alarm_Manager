package com.arash.altafi.alarmmanager.sample1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.alarmmanager.R
import com.google.android.material.button.MaterialButton

class AlarmManagerActivity1 : AppCompatActivity() {

    private lateinit var btnAlarm: MaterialButton
    private val alarm = AlarmSettingManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_manager1)

        init()
    }

    private fun init() {
        btnAlarm = findViewById(R.id.btn_alarm_1)
        btnAlarm.setOnClickListener {
            for (i in AlarmDataList) {
                alarm.setAlarmManager(i)
            }
        }
    }

}