package com.lab.greenpremium.ui.screens.main.contacts

import com.example.myapplication.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides


@Module
internal class ContactsModule(val view: ContactsContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): ContactsContract.View {
        return view
    }

}
