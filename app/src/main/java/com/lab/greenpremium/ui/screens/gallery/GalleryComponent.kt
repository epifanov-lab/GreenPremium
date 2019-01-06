package com.lab.greenpremium.ui.screens.gallery

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [GalleryModule::class, GpApiModule::class])
internal interface GalleryComponent {
    fun inject(activity: GalleryActivity)
}
