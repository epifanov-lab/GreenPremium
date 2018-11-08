package com.lab.greenpremium.ui.screen.main.portfolio

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class PortfolioModule(val view: PortfolioContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): PortfolioContract.View {
        return view
    }

}
