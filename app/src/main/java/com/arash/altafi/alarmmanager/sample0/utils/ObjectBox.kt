package com.arash.altafi.alarmmanager.sample0.utils

import android.content.Context
import com.arash.altafi.alarmmanager.sample0.repository.MyObjectBox
import io.objectbox.Box
import io.objectbox.BoxStore

object ObjectBox {
    lateinit var store: BoxStore private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

    inline fun <reified T> getBox(): Box<T> {
        return store.boxFor(T::class.java)
    }
}