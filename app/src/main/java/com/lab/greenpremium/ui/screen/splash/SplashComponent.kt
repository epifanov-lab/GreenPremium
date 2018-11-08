package com.lab.greenpremium.ui.screen.splash

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [SplashModule::class, GpApiModule::class])
internal interface SplashComponent {
    fun inject(activity: SplashActivity)
}
