package com.lab.greenpremium.ui.screen.main.portfolio

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [PortfolioModule::class, GpApiModule::class])
internal interface PortfolioComponent {
    fun inject(fragment: PortfolioFragment)
}
