package com.lab.greenpremium.ui.screen.main.favorites

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class FavoritesModule(val view: FavoritesContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): FavoritesContract.View {
        return view
    }

}
