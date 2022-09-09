package com.arash.altafi.alarmmanager.sample3

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.*

class BootCompleteNotificationPublisher : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val sharedPrefs = SharedPrefs(context)
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            if (!sharedPrefs.getNotification()) {
                val alarmIntent = Intent(context, AlarmReceiver::class.java)
                alarmIntent.putExtra("sender2", "horoscope")
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    0, alarmIntent,
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        PendingIntent.FLAG_IMMUTABLE else 0x0
                            or
                            PendingIntent.FLAG_UPDATE_CURRENT
                )
                val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                /*               Date date = new Date();
                    //Calendar cal_now = Calendar.getInstance();
                    //cal_now.setTime(date);*/
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = System.currentTimeMillis()
                calendar[Calendar.HOUR_OF_DAY] = 10
                calendar[Calendar.MINUTE] = 0
                calendar[Calendar.SECOND] = 1
                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 1)
                }
                manager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY, pendingIntent
                )
            } else {

                // on device boot complete, reset the alarm
                val alarmIntent = Intent(context, AlarmReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    context, 0, alarmIntent,
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        PendingIntent.FLAG_IMMUTABLE else 0x0
                            or
                            PendingIntent.FLAG_UPDATE_CURRENT
                )
                val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                //val date = Date()
                //Calendar cal_now = Calendar.getInstance();
                //cal_now.setTime(date);
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = System.currentTimeMillis()
                calendar[Calendar.HOUR_OF_DAY] = 10
                calendar[Calendar.MINUTE] = 0
                calendar[Calendar.SECOND] = 1
                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 1)
                }
                manager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY, pendingIntent
                )
            }
        }
    }
}