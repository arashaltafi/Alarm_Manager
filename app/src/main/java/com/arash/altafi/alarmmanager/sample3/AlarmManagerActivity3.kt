package com.arash.altafi.alarmmanager.sample3

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.arash.altafi.alarmmanager.R
import com.google.android.material.button.MaterialButton
import java.util.*

class AlarmManagerActivity3 : AppCompatActivity() {

    private var hr = 0
    private var min = 0
    private var calendar: Calendar? = null
    private lateinit var timeSet: String
    private lateinit var btnAlarm: MaterialButton
    private lateinit var displayTime: TextView
    private var prefs: SharedPrefs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_manager3)

        init()
    }

    private fun init() {

        btnAlarm = findViewById(R.id.btn_alarm_3)
        displayTime = findViewById(R.id.DisplayTime)

        prefs = SharedPrefs(this)
        val c = Calendar.getInstance()
        calendar = Calendar.getInstance()

        hr = c[Calendar.HOUR_OF_DAY]
        min = c[Calendar.MINUTE]

        btnAlarm.setOnClickListener {
            showDialog(333)
        }
    }

    private fun setUserDefinedAlarm() {
        prefs?.setAlarm(true)
        val am: AlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        //intent.putExtra("des",Description);
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        am.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, calendar!!.timeInMillis, AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Toast.makeText(this, "Notification Time Confirmed!", Toast.LENGTH_LONG).show()
    }

    @SuppressLint("SetTextI18n")
    private fun updateTime(hours: Int, mins: Int) {
        var hour = hours
        timeSet = ""
        when {
            hours > 12 -> {
                hour -= 12
                timeSet = "PM"
            }
            hours == 0 -> {
                hour += 12
                timeSet = "AM"
            }
            hours == 12 -> timeSet = "PM"
            else -> timeSet = "AM"
        }
        val minutes: String = if (mins < 10) "0$mins" else mins.toString()
        val aTime = StringBuilder().append(hours).append(':').append(minutes).append(" ").append(timeSet).toString()
        displayTime.text = "Default time is $aTime"
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateDialog(id: Int): Dialog? {
        return if (id == 333) {
            TimePickerDialog(
                this,
                timePickerListener, hr, min, false
            )
        } else null
    }

    private val timePickerListener =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minutes ->
            hr = hourOfDay
            min = minutes

            calendar!!.timeInMillis = System.currentTimeMillis()
            calendar!![Calendar.HOUR_OF_DAY] = hr
            calendar!![Calendar.MINUTE] = min
            calendar!![Calendar.SECOND] = 1
            if (calendar!!.before(Calendar.getInstance())) {
                calendar!!.add(Calendar.DATE, 1)
            }
            updateTime(hr, min)
            setUserDefinedAlarm()
        }

}