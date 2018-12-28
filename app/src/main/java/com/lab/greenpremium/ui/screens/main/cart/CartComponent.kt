package com.lab.greenpremium.ui.screens.main.cart

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [CartModule::class, GpApiModule::class])
internal interface CartComponent {
    fun inject(fragment: CartFragment)
}
