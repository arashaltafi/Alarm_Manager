package com.arash.altafi.alarmmanager.sample0.di

import android.app.Application
import android.content.Context
import com.arash.altafi.alarmmanager.sample0.repository.ReminderManager
import com.arash.altafi.alarmmanager.sample0.repository.ReminderSetter
import com.arash.altafi.alarmmanager.sample0.repository.ReminderStorage
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideReminderUtils(
        context: Context,
    ) = ReminderManager(
        ReminderStorage(), ReminderSetter(context)
    )

}