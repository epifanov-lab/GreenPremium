package com.lab.greenpremium.ui.screen.start

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [StartModule::class, GpApiModule::class])
internal interface StartComponent {
    fun inject(activity: StartActivity)
}
