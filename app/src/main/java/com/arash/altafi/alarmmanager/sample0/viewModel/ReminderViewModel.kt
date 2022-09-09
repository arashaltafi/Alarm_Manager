package com.arash.altafi.alarmmanager.sample0.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arash.altafi.alarmmanager.sample0.repository.ReminderEntity
import com.arash.altafi.alarmmanager.sample0.repository.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository,
) : ViewModel() {

    private val _liveReminderList = MutableLiveData<List<ReminderEntity>>()
    val liveReminderList: MutableLiveData<List<ReminderEntity>>
        get() = _liveReminderList

    private val _liveActiveReminderCount = MutableLiveData<Int>()
    val liveActiveReminderCount: LiveData<Int>
        get() = _liveActiveReminderCount

    fun checkPermission(result: (can: Boolean) -> Unit) {
        reminderRepository.checkPermission(result)
    }

    fun unregister() {
        reminderRepository.unregister()
    }

    fun add(
        reminder: ReminderEntity,
        currentTime: Long,
        isRefresh: Boolean = false
    ) = callDatabase(
        reminderRepository.add(reminder)
    ) { if (isRefresh) getAll(currentTime) }

    fun get(uniqueId: String, result: (ReminderEntity?) -> Unit) = callDatabase(
        reminderRepository.get(uniqueId),
    ) {
        result.invoke(it)
    }

    fun getAll(currentTime: Long) = callDatabase(
        reminderRepository.getAll()
    ) { list ->
        val activeList = list.filter {
            it.unixTime >= currentTime
        }.sortedBy { it.unixTime }

        val endedList = list.filter {
            it.unixTime <= currentTime
        }.sortedByDescending { it.unixTime }

        _liveReminderList.value = activeList + endedList
    }

    fun remove(
        uniqueId: String,
        currentTime: Long,
        isRefresh: Boolean = false
    ) = callDatabase(
        reminderRepository.remove(uniqueId)
    ) { if (isRefresh) getAll(currentTime) }

    fun removeAll(
        currentTime: Long,
        isRefresh: Boolean = false
    ) = callDatabase(
        reminderRepository.removeAll()
    ) {
        if (isRefresh) getAll(currentTime)
    }

    fun removeOutTime(lowestTime: Long) = callDatabase(
        reminderRepository.removeOutTime(lowestTime)
    )

    fun <T> callDatabase(
        databaseCall: Flow<T>,
        liveResult: MutableLiveData<T>? = null,
        onResponse: ((T) -> Unit)? = null
    ) {
        var dispatchRetry: (() -> Unit)? = null
        dispatchRetry = {
            viewModelScope.launch {
                databaseCall
                    .onStart {
                    }
                    .catch {
                        it.printStackTrace()
                    }
                    .collect {
                        liveResult?.value = it
                        onResponse?.invoke(it)
                    }
            }
        }

        dispatchRetry.invoke()
    }
}