package com.lab.greenpremium.di

import android.content.Context
import com.lab.greenpremium.data.local.PreferencesManager
import com.lab.greenpremium.di.module.AppModule
import com.lab.greenpremium.di.module.NetworkModule
import com.lab.greenpremium.di.module.PreferencesModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, PreferencesModule::class])

interface AppComponent {

    fun context(): Context
    fun retrofit(): Retrofit
    fun preferences(): PreferencesManager

}