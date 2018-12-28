package com.lab.greenpremium.ui.screens.main.plants.sub

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [PlantsSubModule::class, GpApiModule::class])
internal interface PlantsSubComponent {
    fun inject(fragment: PlantsSubFragment)
}
