package com.arash.altafi.alarmmanager.sample1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.arash.altafi.alarmmanager.R

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = 1
        const val PRIMARY_CHANNEL_ID = "AlarmTest_Notification_channel"
    }

    private lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: ")
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp:AlarmTest")
        wl.acquire(10 * 60 * 1000L /*10 minutes*/)
        val alarmSettingManager = AlarmSettingManager(context)
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            for (data in AlarmDataList) {
                alarmSettingManager.setAlarmManager(data)
            }
        } else {
            val type = intent.getIntExtra(context.getString(R.string.app_name), 0)
            if (type == 0) return
            AlarmDataList[type]?.let { alarmSettingManager.setAlarmManager(type, it) }
            val title = AlarmDataList[type]?.title
            val message = AlarmDataList[type]?.message

            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel()
            }
            if (title != null && message != null) {
                deliverNotification(context, context.getString(title), context.getString(message))
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }

        }
        wl.release()
    }

    private fun deliverNotification(context: Context, title: String, message: String) {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp:AlarmTest")
        wl.acquire(10 * 60 * 1000L /*10 minutes*/)

        val contentIntent = Intent(context, AlarmManagerActivity1::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            PRIMARY_CHANNEL_ID,
            "Stand up notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = "AlarmManager Tests"
        notificationManager.createNotificationChannel(notificationChannel)
    }
}