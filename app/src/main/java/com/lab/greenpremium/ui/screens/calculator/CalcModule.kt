package com.lab.greenpremium.ui.screens.calculator

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class CalcModule(val view: CalcContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): CalcContract.View {
        return view
    }

}
