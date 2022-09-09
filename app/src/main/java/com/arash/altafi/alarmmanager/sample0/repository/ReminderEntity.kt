package com.arash.altafi.alarmmanager.sample0.repository

import com.arash.altafi.alarmmanager.sample0.base.BaseResponseData
import com.arash.altafi.alarmmanager.sample0.utils.toIntHash
import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class ReminderEntity(
    @Id
    @SerializedName("id")
    override var id: Long = 0L,
    @SerializedName("unique_id")
    val uniqueId: String = "",
    @SerializedName("unix_time")
    val unixTime: Long = 0L,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("text")
    val text: String = "",
    @SerializedName("channel_id")
    val channelId: String = ""
) : BaseResponseData() {
    fun getUniqueHash(): Int {
        return "${uniqueId}_$unixTime".toIntHash()
    }
}