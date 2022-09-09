package com.arash.altafi.alarmmanager.sample0.repository

import android.app.AlarmManager
import android.app.AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.widget.Toast
import androidx.annotation.RequiresApi
import javax.inject.Inject

class ReminderSetter (private val context: Context) {

    private val alarmManager: AlarmManager by lazy {
        context.getSystemService(
            Context.ALARM_SERVICE
        ) as AlarmManager
    }

    private var alarmReceiver: AlarmReceiver? = null
    private var alarmResult: ((can: Boolean) -> Unit)? = null
    private var alarmAddAgain: (() -> Unit)? = null

    inner class AlarmReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED) {
                alarmAddAgain?.invoke()
                alarmResult?.invoke(true)
            }
        }

    }

    /**
     * don't forget call [unregister]
     */
    fun checkPermission(result: (can: Boolean) -> Unit, addAgain: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (hasPermission())
                result.invoke(true)
            else {
                Toast.makeText(context, "برای فعال سازی این قابلیت، تنظیمات برنامه را فعال کنید.", Toast.LENGTH_SHORT).show()
                alarmResult = result
                alarmAddAgain = addAgain
                alarmReceiver = AlarmReceiver()

                context.registerReceiver(
                    alarmReceiver,
                    IntentFilter(ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED)
                )


                Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(it)
                }
            }
        } else {
            result.invoke(true)
        }
    }

    fun unregister() {
        alarmReceiver?.let {
            context.unregisterReceiver(it)
            alarmResult = null
            alarmReceiver = null
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun hasPermission(): Boolean = alarmManager.canScheduleExactAlarms()

    /**
     * call [checkPermission] before call it
     */
    fun add(reminder: ReminderEntity): ReminderEntity {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("uniqueId", reminder.uniqueId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context.applicationContext, reminder.getUniqueHash(), intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                PendingIntent.FLAG_IMMUTABLE else 0x0
                    or
                    PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(
            RTC_WAKEUP, reminder.unixTime, pendingIntent
        )

        return reminder
    }

    fun remove(reminder: ReminderEntity): ReminderEntity {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("uniqueId", reminder.uniqueId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context.applicationContext, reminder.getUniqueHash(), intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                PendingIntent.FLAG_IMMUTABLE else 0x0
                    or
                    PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.cancel(pendingIntent)
        return reminder
    }
}