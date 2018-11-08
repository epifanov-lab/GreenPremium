package com.lab.greenpremium.ui.screen.main

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [MainModule::class, GpApiModule::class])
internal interface MainComponent {
    fun inject(activity: MainActivity)
}
