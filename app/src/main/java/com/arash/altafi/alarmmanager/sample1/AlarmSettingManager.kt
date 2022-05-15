package com.arash.altafi.alarmmanager.sample1

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.arash.altafi.alarmmanager.R
import java.util.*

class AlarmSettingManager(private val context: Context) {
    private val alarmManager: AlarmManager by lazy {
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun setAlarmManager(data: MutableMap.MutableEntry<Int, AlarmData>) {
        Log.d(TAG, "setAlarmManager: ${data.key}")
        val pendingIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.putExtra(context.getString(R.string.app_name), data.key)
            PendingIntent.getBroadcast(context, data.key, intent, PendingIntent.FLAG_IMMUTABLE)
        }

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, data.value.hour)
            set(Calendar.MINUTE, data.value.min)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    fun setAlarmManager(key: Int, value: AlarmData) {
        Log.d(TAG, "setAlarmManager: $key")
        val pendingIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.putExtra(context.getString(R.string.app_name), key)
            PendingIntent.getBroadcast(context, key, intent, PendingIntent.FLAG_IMMUTABLE)
        }

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, value.hour)
            set(Calendar.MINUTE, value.min)
            add(Calendar.DATE, 1)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}