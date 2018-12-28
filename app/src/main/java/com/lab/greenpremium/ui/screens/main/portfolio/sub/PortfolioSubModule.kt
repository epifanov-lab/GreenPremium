package com.lab.greenpremium.ui.screens.main.portfolio.sub

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class PortfolioSubModule(val view: PortfolioSubContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): PortfolioSubContract.View {
        return view
    }

}
