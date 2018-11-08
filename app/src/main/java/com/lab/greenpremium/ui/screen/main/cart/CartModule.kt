package com.lab.greenpremium.ui.screen.main.cart

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class CartModule(val view: CartContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): CartContract.View {
        return view
    }

}
