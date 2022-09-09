package com.arash.altafi.alarmmanager.sample0.repository

import android.os.Build
import androidx.annotation.RequiresApi
import javax.inject.Inject

class ReminderManager(
    private val reminderStorage: ReminderStorage,
    private val reminderSetter: ReminderSetter
) {

    @RequiresApi(Build.VERSION_CODES.S)
    fun hasPermission(): Boolean = reminderSetter.hasPermission()

    fun checkPermission(result: (can: Boolean) -> Unit) {
        reminderSetter.checkPermission(result) {
            reboot(System.currentTimeMillis())
        }
    }

    fun unregister() {
        reminderSetter.unregister()
    }

    fun add(reminder: ReminderEntity): Boolean {
        get(reminder.uniqueId)?.apply {
            remove(reminder.uniqueId)
        }

        return reminderStorage.add(reminderSetter.add(reminder))
    }

    fun get(uniqueId: String): ReminderEntity? {
        return reminderStorage.get(uniqueId)
    }

    fun getAll(): List<ReminderEntity> {
        return reminderStorage.getAll()
    }

    fun getActiveCount(currentTime: Long): Int {
        return reminderStorage.getActiveCount(currentTime)
    }

    fun remove(uniqueId: String): Boolean {
        reminderStorage.get(uniqueId)?.let {
            reminderSetter.remove(it)
            reminderStorage.remove(it)
            return true
        } ?: return false
    }

    fun removeAll() {
        reminderStorage.getAll().forEach {
            reminderSetter.remove(it)
        }

        reminderStorage.removeAll()
    }

    /**
     *   add all alarm manager after reboot OS and call boot broadcast
     **/
    fun reboot(currentTime: Long = -1) {
        reminderStorage.getAll(currentTime).forEach {
            reminderSetter.add(it)
        }
    }

    fun removeOutTime(lowestTime: Long): Boolean {
        return reminderStorage.removeOutTime(lowestTime)
    }
}