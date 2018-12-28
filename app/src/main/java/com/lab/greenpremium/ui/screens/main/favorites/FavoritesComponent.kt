package com.lab.greenpremium.ui.screens.main.favorites

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [FavoritesModule::class, GpApiModule::class])
internal interface FavoritesComponent {
    fun inject(fragment: FavoritesFragment)
}
