package com.lab.greenpremium.ui.screens.auth

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [AuthModule::class, GpApiModule::class])
internal interface AuthComponent {
    fun inject(authActivity: AuthActivity)
}
