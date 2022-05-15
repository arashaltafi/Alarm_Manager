package com.arash.altafi.alarmmanager.sample1

import com.arash.altafi.alarmmanager.R

const val TAG = "winter"

const val SLEEP_DIARY = 1000
const val MORNING = 2000
const val EVENING = 3000
const val SLEEP = 4000
const val MISS_DIARY = 5000


val AlarmDataList: HashMap<Int, AlarmData> = HashMap<Int, AlarmData>().apply {
    put(SLEEP_DIARY, AlarmData(23, 17, R.string.notify_sleep_diary_title, R.string.notify_sleep_diary_message))
    put(MORNING, AlarmData(23, 25, R.string.notify_morning_title, R.string.notify_morning_message))
    put(EVENING, AlarmData(23, 55, R.string.notify_evening_title, R.string.notify_evening_message))
    put(SLEEP, AlarmData(0, 15, R.string.notify_sleep_title, R.string.notify_sleep_message))
    put(MISS_DIARY, AlarmData(0, 35, R.string.notify_miss_diary_title, R.string.notify_miss_diary_message))
}

data class AlarmData(
    val hour: Int,
    val min: Int,
    val title: Int,
    val message: Int,
)
