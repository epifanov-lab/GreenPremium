package com.lab.greenpremium.ui.screens.splash

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class SplashModule(val view: SplashContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): SplashContract.View {
        return view
    }

}
