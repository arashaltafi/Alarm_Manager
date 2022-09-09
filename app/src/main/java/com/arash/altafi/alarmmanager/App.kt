package com.arash.altafi.alarmmanager

import androidx.multidex.MultiDexApplication
import com.arash.altafi.alarmmanager.sample0.utils.ObjectBox
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}