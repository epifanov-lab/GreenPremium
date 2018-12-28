package com.lab.greenpremium.ui.screens.delivery

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class DeliveryModule(val view: DeliveryContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): DeliveryContract.View {
        return view
    }

}
