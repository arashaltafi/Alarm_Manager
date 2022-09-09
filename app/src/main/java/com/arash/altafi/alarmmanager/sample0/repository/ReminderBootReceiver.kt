package com.arash.altafi.alarmmanager.sample0.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReminderBootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var reminderManager: ReminderManager

    @RequiresApi(Build.VERSION_CODES.S)
    @CallSuper
    override fun onReceive(context: Context?, intent: Intent?) {
        if (reminderManager.hasPermission())
            context?.let { reminderManager.reboot(System.currentTimeMillis()) }

    }


}