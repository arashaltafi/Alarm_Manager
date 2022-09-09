package com.arash.altafi.alarmmanager.sample0.repository

import com.arash.altafi.alarmmanager.sample0.utils.ObjectBox

class ReminderStorage {

    private val box by lazy {
        ObjectBox.getBox<ReminderEntity>()
    }

    fun add(reminder: ReminderEntity): Boolean {
        return box.put(reminder) > 0L
    }

    fun get(uniqueId: String): ReminderEntity? {
        val query = box.query()
            .equal(ReminderEntity_.uniqueId, uniqueId)
            .build()

        val reminder = query.findFirst()
        query.close()

        return reminder
    }

    fun getAll(currentTime: Long = -1): List<ReminderEntity> {
        return if (currentTime == -1L) box.all else box.query()
            .greater(ReminderEntity_.unixTime, currentTime)
            .or().equal(ReminderEntity_.unixTime, currentTime)
            .build().find()
    }

    fun getActiveCount(currentTime: Long): Int {
        val query = box.query()
            .greater(ReminderEntity_.unixTime, currentTime)
            .or().equal(ReminderEntity_.unixTime, currentTime)
            .build()

        val reminder = query.find().size
        query.close()

        return reminder
    }

    fun remove(reminder: ReminderEntity): Boolean {
        return box.remove(reminder)
    }

    fun removeAll(): Boolean {
        box.removeAll()
        return true
    }

    fun removeOutTime(lowestTime: Long): Boolean {
        val query = box.query()
            .less(ReminderEntity_.unixTime, lowestTime)
            .build()

        query.remove()
        query.close()

        return true
    }
}