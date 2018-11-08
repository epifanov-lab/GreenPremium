package com.lab.greenpremium.ui.screen.plant_detail

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [PlantDetailModule::class, GpApiModule::class])
internal interface PlantDetailComponent {
    fun inject(activity: PlantDetailActivity)
}
