package com.lab.greenpremium.di

import android.content.Context
import com.lab.greenpremium.di.module.AppModule
import com.lab.greenpremium.di.module.NetworkModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])

interface AppComponent {

    fun context(): Context
    fun retrofit(): Retrofit

}