package com.lab.greenpremium.ui.screen.auth

import android.annotation.SuppressLint
import com.lab.greenpremium.R
import com.lab.greenpremium.data.Repository
import com.lab.greenpremium.data.network.CallbackListener
import io.reactivex.Observable
import javax.inject.Inject


class AuthPresenter @Inject constructor(val view: AuthContract.View) : AuthContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    private var login: String? = null
    private var password: String? = null

    @SuppressLint("CheckResult")
    override fun initializeDataInput(login: Observable<String>, password: Observable<String>) {

        login.subscribe { s ->
            run {
                if (!s.isEmpty()) {
                    this.login = s
                    view.setLoginInputError(null)
                } else {
                    this.login = null
                }
            }
        }

        password.subscribe { s ->
            run {
                if (!s.isEmpty()) {
                    this.password = s
                    view.setPasswordInputError(null)
                } else {
                    this.password = null
                }
            }
        }
    }

    override fun validateDataAndProceedAuth() {
        when {

            login == null -> {
                view.setLoginInputError(R.string.error_empty_field, R.string.title_email)
                return
            }

            password == null -> {
                view.setPasswordInputError(R.string.error_empty_field, R.string.title_password)
                return
            }

            else -> {
                view.setLoginInputError(null)
                view.setPasswordInputError(null)

                repository.auth(login!!, password!!, object : CallbackListener {
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
    }
}