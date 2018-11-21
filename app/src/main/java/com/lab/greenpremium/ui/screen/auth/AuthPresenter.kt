package com.lab.greenpremium.ui.screen.auth

import com.lab.greenpremium.data.Repository
import com.lab.greenpremium.data.network.CallbackListener
import javax.inject.Inject


class AuthPresenter @Inject constructor(val view: AuthContract.View) : AuthContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun auth(login: String, password: String) {
        repository.auth(login, password, object : CallbackListener {
            override fun doBefore() {
                view.showLoadingDialog(true)
            }

            override fun doAfter() {
                view.showLoadingDialog(false)
            }

            override fun onError(throwable: Throwable) {
                view.showError(throwable.message)
            }

            override fun onSuccess() {
                view.goToMain()
            }
        })
    }
}