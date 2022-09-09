package com.arash.altafi.alarmmanager.sample0.di

import com.arash.altafi.alarmmanager.sample0.ui.ReminderAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object AdaptersModule {

    @FragmentScoped
    @Provides
    fun provideReminderAdapter() = ReminderAdapter()

}