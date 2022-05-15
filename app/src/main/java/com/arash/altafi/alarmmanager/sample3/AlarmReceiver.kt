package com.arash.altafi.alarmmanager.sample3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.arash.altafi.alarmmanager.R

class AlarmReceiver : BroadcastReceiver() {
    private var channelId = "myChannel"
    private var nm: NotificationManager? = null
    private var mChannel: NotificationChannel? = null
    private var pendingIntent: PendingIntent? = null
    private var sharedPrefs: SharedPrefs? = null

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        sharedPrefs = SharedPrefs(context)
        nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val myNew = Intent(context, AlarmManagerActivity3::class.java)
        pendingIntent = PendingIntent.getActivity(
            context, 0, myNew,
            PendingIntent.FLAG_IMMUTABLE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!sharedPrefs!!.getNotification()) {
                val name: CharSequence = "myChannel1"
                val importance = NotificationManager.IMPORTANCE_HIGH
                mChannel = NotificationChannel(channelId, name, importance)
                nm!!.createNotificationChannel(mChannel!!)
                val builder = NotificationCompat.Builder(context, channelId)
                builder.setAutoCancel(true)
                builder.setSmallIcon(R.mipmap.ic_launcher)
                builder.setContentTitle("Horoscope Updates")
                builder.setContentText("Tap to see daily horoscope.")
                builder.setContentIntent(pendingIntent)
                builder.setOngoing(false)
                nm!!.notify(1001, builder.build())
            } else {
                val name: CharSequence = "myChannel1"
                val importance = NotificationManager.IMPORTANCE_HIGH
                mChannel = NotificationChannel(channelId, name, importance)
                nm!!.createNotificationChannel(mChannel!!)
                val builder = NotificationCompat.Builder(context, channelId)
                builder.setAutoCancel(true)
                builder.setSmallIcon(R.mipmap.ic_launcher)
                builder.setContentTitle(context.getString(R.string.app_name))
                builder.setContentText("This is your custom notification.")
                builder.setContentIntent(pendingIntent)
                builder.setOngoing(false)
                nm!!.notify(1001, builder.build())
            }
        } else {
            val builder = NotificationCompat.Builder(context, channelId)
            builder.setAutoCancel(true)
            builder.setSmallIcon(R.mipmap.ic_launcher)
            builder.setContentTitle(context.getString(R.string.app_name))
            builder.setContentText("This is your custom notification.")
            builder.setContentIntent(pendingIntent)
            builder.setOngoing(false)
            nm!!.notify(1001, builder.build())
        }
    }
}