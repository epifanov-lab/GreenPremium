package com.lab.greenpremium.ui.screen.main.contacts.meet

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class MeetingModule(val view: MeetingContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): MeetingContract.View {
        return view
    }

}
