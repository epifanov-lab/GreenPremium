package com.lab.greenpremium.ui.screens.main.contacts

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ContactsModule::class, GpApiModule::class])
internal interface ContactsComponent {
    fun inject(fragment: ContactsFragment)
}
