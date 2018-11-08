package com.lab.greenpremium.ui.screen.plant_detail

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class PlantDetailModule(val view: PlantDetailContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): PlantDetailContract.View {
        return view
    }

}
