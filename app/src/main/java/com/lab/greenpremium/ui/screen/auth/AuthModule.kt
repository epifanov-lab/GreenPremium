package com.lab.greenpremium.ui.screen.auth

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * Created by user on 7/14/17.
 */

@Module
internal class AuthModule(val view: AuthContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): AuthContract.View {
        return view
    }

}
