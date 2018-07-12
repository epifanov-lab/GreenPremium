package com.lab.greenpremium.di.module


import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class AppModule(var appContext: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return appContext
    }
}
