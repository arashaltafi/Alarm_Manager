package com.arash.altafi.alarmmanager.sample1

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.arash.altafi.alarmmanager.R
import java.util.*

class AlarmSettingManager(private val context: Context) {
    private val alarmManager: AlarmManager by lazy {
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setAlarmManager(data: MutableMap.MutableEntry<Int, AlarmData>) {
        Log.d(TAG, "setAlarmManager: ${data.key}")
        val pendingIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.putExtra(context.getString(R.string.app_name), data.key)
            PendingIntent.getBroadcast(context, data.key, intent, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                PendingIntent.FLAG_IMMUTABLE else 0x0
                    or
                    PendingIntent.FLAG_UPDATE_CURRENT
            )
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

    @RequiresApi(Build.VERSION_CODES.M)
    fun setAlarmManager(key: Int, value: AlarmData) {
        Log.d(TAG, "setAlarmManager: $key")
        val pendingIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.putExtra(context.getString(R.string.app_name), key)
            PendingIntent.getBroadcast(context, key, intent,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    PendingIntent.FLAG_IMMUTABLE else 0x0
                        or
                        PendingIntent.FLAG_UPDATE_CURRENT
            )
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