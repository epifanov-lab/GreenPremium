package com.lab.greenpremium.ui.screen.message

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [MessageModule::class, GpApiModule::class])
internal interface MessageComponent {
    fun inject(activity: MessageActivity)
}
