package com.arash.altafi.alarmmanager.sample0.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arash.altafi.alarmmanager.databinding.ActivityAlarmManager0Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmManagerActivity0 : AppCompatActivity() {

    private val binding by lazy {
        ActivityAlarmManager0Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

}