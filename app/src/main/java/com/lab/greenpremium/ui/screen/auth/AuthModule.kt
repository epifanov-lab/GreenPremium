package com.lab.greenpremium.ui.screen.auth

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class AuthModule(val view: AuthContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): AuthContract.View {
        return view
    }

}
