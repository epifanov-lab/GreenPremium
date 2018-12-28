package com.lab.greenpremium.ui.screens.main.map

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [MapModule::class, GpApiModule::class])
internal interface MapComponent {
    fun inject(fragment: MapFragment)
}
