package com.lab.greenpremium.ui.screens.splash

import com.lab.greenpremium.data.repo.Repository
import javax.inject.Inject

class SplashPresenter @Inject constructor(val view: SplashContract.View) : SplashContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        if (repository.isAuthorized() && !repository.isInDemoMode()) view.goMainScreen()
        else view.goStartScreen()
    }
}