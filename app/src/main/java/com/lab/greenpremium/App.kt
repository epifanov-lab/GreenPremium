package com.lab.greenpremium

import android.app.Application
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.DaggerAppComponent
import com.lab.greenpremium.di.module.AppModule

lateinit var APP: App

class App : Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        APP = this
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}
