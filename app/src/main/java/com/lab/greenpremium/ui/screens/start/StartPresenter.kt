package com.lab.greenpremium.ui.screens.start

import com.lab.greenpremium.DEMO_USER_LOGIN
import com.lab.greenpremium.DEMO_USER_PASSWORD
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repository.Repository
import javax.inject.Inject

class StartPresenter @Inject constructor(val view: StartContract.View) : StartContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        view.initializeGradientTitle()
    }

    override fun onClickAuth() {
        view.goToAuth()
    }

    override fun onClickDemo() {
        repository.auth(DEMO_USER_LOGIN, DEMO_USER_PASSWORD, object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                this@StartPresenter.view.goToMain()
            }
        })
    }

    override fun onClickSupport() {
        view.callToOffice()
    }
}