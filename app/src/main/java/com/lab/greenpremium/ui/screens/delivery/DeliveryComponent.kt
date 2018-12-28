package com.lab.greenpremium.ui.screens.delivery

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [DeliveryModule::class, GpApiModule::class])
internal interface DeliveryComponent {
    fun inject(activity: DeliveryActivity)
}
