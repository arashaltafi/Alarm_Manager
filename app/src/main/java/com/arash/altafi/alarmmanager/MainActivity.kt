package com.arash.altafi.alarmmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.alarmmanager.sample0.ui.AlarmManagerActivity0
import com.arash.altafi.alarmmanager.sample1.AlarmManagerActivity1
import com.arash.altafi.alarmmanager.sample2.AlarmManagerActivity2
import com.arash.altafi.alarmmanager.sample3.AlarmManagerActivity3
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var btnAlarm0: MaterialButton
    private lateinit var btnAlarm1: MaterialButton
    private lateinit var btnAlarm2: MaterialButton
    private lateinit var btnAlarm3: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {

        btnAlarm0 = findViewById(R.id.btn_alarm_manager_0)
        btnAlarm1 = findViewById(R.id.btn_alarm_manager_1)
        btnAlarm2 = findViewById(R.id.btn_alarm_manager_2)
        btnAlarm3 = findViewById(R.id.btn_alarm_manager_3)

        btnAlarm0.setOnClickListener {
            startActivity(Intent(this, AlarmManagerActivity0::class.java))
        }

        btnAlarm1.setOnClickListener {
            startActivity(Intent(this, AlarmManagerActivity1::class.java))
        }

        btnAlarm2.setOnClickListener {
            startActivity(Intent(this, AlarmManagerActivity2::class.java))
        }

        btnAlarm3.setOnClickListener {
            startActivity(Intent(this, AlarmManagerActivity3::class.java))
        }
    }

}