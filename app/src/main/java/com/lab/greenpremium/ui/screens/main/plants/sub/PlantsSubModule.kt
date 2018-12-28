package com.lab.greenpremium.ui.screens.main.plants.sub

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class PlantsSubModule(val view: PlantsSubContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): PlantsSubContract.View {
        return view
    }

}
