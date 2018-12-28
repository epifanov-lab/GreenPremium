package com.lab.greenpremium.ui.screens.main.plants

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [PlantsModule::class, GpApiModule::class])
internal interface PlantsComponent {
    fun inject(fragment: PlantsFragment)
}
