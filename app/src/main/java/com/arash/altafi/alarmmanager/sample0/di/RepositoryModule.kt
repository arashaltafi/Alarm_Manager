package com.arash.altafi.alarmmanager.sample0.di

import com.arash.altafi.alarmmanager.sample0.repository.ReminderManager
import com.arash.altafi.alarmmanager.sample0.repository.ReminderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideReminder(
        reminderManager: ReminderManager
    ) = ReminderRepository(reminderManager)

}