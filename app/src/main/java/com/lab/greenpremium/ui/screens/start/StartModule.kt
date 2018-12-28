package com.lab.greenpremium.ui.screens.start

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class StartModule(val view: StartContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): StartContract.View {
        return view
    }

}
