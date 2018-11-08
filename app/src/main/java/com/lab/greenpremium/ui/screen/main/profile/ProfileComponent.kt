package com.lab.greenpremium.ui.screen.main.profile

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ProfileModule::class, GpApiModule::class])
internal interface ProfileComponent {
    fun inject(fragment: ProfileFragment)
}
