package com.lab.greenpremium.ui.screen.message

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class MessageModule(val view: MessageContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): MessageContract.View {
        return view
    }

}
