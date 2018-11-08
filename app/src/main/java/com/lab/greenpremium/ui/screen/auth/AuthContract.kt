package com.lab.greenpremium.ui.screen.auth

import com.lab.greenpremium.ui.screen.base.BaseContract

interface AuthContract {
    interface View : BaseContract.BaseView {
        fun goToMain()
    }

    interface Presenter {
        fun auth(login: String, password: String)
    }
}