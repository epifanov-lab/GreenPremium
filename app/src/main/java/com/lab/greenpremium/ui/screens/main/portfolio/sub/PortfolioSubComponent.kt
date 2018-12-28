package com.lab.greenpremium.ui.screens.main.portfolio.sub

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [PortfolioSubModule::class, GpApiModule::class])
internal interface PortfolioSubComponent {
    fun inject(fragment: PortfolioSubFragment)
}
