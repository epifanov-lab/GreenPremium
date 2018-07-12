package com.lab.greenpremium

import android.app.Application
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.DaggerAppComponent

lateinit var APP: App

abstract class App : Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        APP = this
        component = DaggerAppComponent.builder()
                .build()
    }
}
