package com.arash.altafi.alarmmanager.sample2

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.util.*

class PollReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Tick Tick Tick", Toast.LENGTH_LONG).show()
        Log.d("test123321", "onReceive()")
        Log.d("test123321", Calendar.getInstance().time.toString())
        ScheduledService.enqueueWork(context)
    }

    companion object {
        internal fun scheduleAlarms(context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, PollReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 15)

            // for testing: adb shell dumpsys deviceIdle | grep mState
            // for testing: adb shell dumpsys battery unplug

            // DEMO : setAlarmClock
//            alarmManager.setAlarmClock(
//                AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
//                pendingIntent
//            )

            // DEMO : AlarmManager.RTC
            //  This alarm does not wake the device up; if it goes off while the device is asleep, it will not be
            // delivered until the next time the device wakes up.
            // DEMO : AlarmManager.RTC_WAKEUP : Opposite to  AlarmManager.RTC it will wake device

            /**
             *  AlarmManager.set():  "batch" alarm
             */
//            alarmManager.set(AlarmManager.RTC, calendar.timeInMillis, pendingIntent)
//            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

            /**
             *  AlarmManager.setExact(): No batch alarm
             */
//            alarmManager.setExact(AlarmManager.RTC, calendar.timeInMillis, pendingIntent)
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

            /**
             *  AlarmManager.setExactAndAllowWhileIdle()
             */
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

            Log.d("test123321", "scheduleAlarms()")
            Log.d("test123321", calendar.time.toString())
        }
    }
}
