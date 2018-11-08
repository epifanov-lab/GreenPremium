package com.lab.greenpremium.ui.screen.main.map

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class MapModule(val view: MapContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): MapContract.View {
        return view
    }

}
