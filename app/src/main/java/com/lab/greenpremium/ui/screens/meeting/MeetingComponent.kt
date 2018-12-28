package com.lab.greenpremium.ui.screens.meeting

import com.example.myapplication.di.scopes.ActivityScope
import com.lab.greenpremium.di.AppComponent
import com.lab.greenpremium.di.module.GpApiModule
import dagger.Component


@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [MeetingModule::class, GpApiModule::class])
internal interface MeetingComponent {
    fun inject(activity: MeetingActivity)
}
