package com.lab.greenpremium.ui.screens.main.profile

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class ProfileModule(val view: ProfileContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): ProfileContract.View {
        return view
    }

}
