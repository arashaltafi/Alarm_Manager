package com.arash.altafi.alarmmanager.sample0.base

import com.google.gson.annotations.SerializedName

abstract class BaseResponseData(
    @SerializedName("id")
    open var id: Long = default_id
) {

    companion object {
        const val default_id = 0L
        private const val serialVersionUID: Long = 7383408148346419009
    }

    override fun toString(): String {
        return "BaseResponseData: id=$id"
    }
}