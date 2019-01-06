package com.lab.greenpremium.ui.screens.gallery

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class GalleryModule(val view: GalleryContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): GalleryContract.View {
        return view
    }

}
