package com.lab.greenpremium.ui.screens.main.plants

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class PlantsModule(val view: PlantsContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): PlantsContract.View {
        return view
    }

}
