package com.lab.greenpremium.ui.screen.main

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class MainModule(val view: MainContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): MainContract.View {
        return view
    }

}
