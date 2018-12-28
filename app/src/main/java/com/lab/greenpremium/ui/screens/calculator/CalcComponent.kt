package com.lab.greenpremium.ui.screens.calculator

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [CalcModule::class, GpApiModule::class])
internal interface CalcComponent {
    fun inject(activity: CalcActivity)
}
