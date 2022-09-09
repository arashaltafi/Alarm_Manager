package com.arash.altafi.alarmmanager.sample0.di

import androidx.fragment.app.Fragment
import com.arash.altafi.alarmmanager.sample0.base.BaseFragment
import com.arash.altafi.alarmmanager.sample0.utils.cast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @FragmentScoped
    @Provides
    fun provideBaseFragment(fragment: Fragment): BaseFragment<*> = fragment.cast()!!
}