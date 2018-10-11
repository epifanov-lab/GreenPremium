package com.lab.greenpremium.di.module

import android.content.Context
import com.lab.greenpremium.data.local.PreferencesManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferencesModule(context: Context) {
    var preferencesManager: PreferencesManager = PreferencesManager(context)

    @Provides
    @Singleton
    internal fun providePreferencesManager(): PreferencesManager {
        return preferencesManager
    }
}
