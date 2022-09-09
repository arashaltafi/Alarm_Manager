package com.arash.altafi.alarmmanager.sample0.repository

import com.arash.altafi.alarmmanager.sample0.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderRepository @Inject constructor(
    private val reminderManager: ReminderManager
) : BaseRepository() {

    fun checkPermission(result: (can: Boolean) -> Unit) {
        reminderManager.checkPermission(result)
    }

    fun unregister() {
        reminderManager.unregister()
    }

    fun add(reminder: ReminderEntity): Flow<Boolean> {
        return callDatabase { reminderManager.add(reminder) }
    }

    fun get(uniqueId: String): Flow<ReminderEntity?> {
        return callDatabase { reminderManager.get(uniqueId) }
    }

    fun getAll(): Flow<List<ReminderEntity>> {
        return callDatabase { reminderManager.getAll() }
    }

    fun getActiveCount(currentTime: Long): Flow<Int> {
        return callDatabase { reminderManager.getActiveCount(currentTime) }
    }

    fun remove(uniqueId: String): Flow<Boolean> {
        return callDatabase { reminderManager.remove(uniqueId) }
    }

    fun removeAll(): Flow<Unit> {
        return callDatabase { reminderManager.removeAll() }
    }

    fun removeOutTime(lowestTime: Long): Flow<Boolean> {
        return callDatabase { reminderManager.removeOutTime(lowestTime) }
    }
}