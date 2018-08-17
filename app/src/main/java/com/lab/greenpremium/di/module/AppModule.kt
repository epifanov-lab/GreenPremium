package com.lab.greenpremium.di.module


import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var appContext: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return appContext
    }
}
