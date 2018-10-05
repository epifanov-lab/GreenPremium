package com.lab.greenpremium.ui.screen.auth

import com.lab.greenpremium.ui.screen.base.BaseContract


/**
 * Created by user on 7/14/17.
 */
interface AuthContract {
    interface View : BaseContract.BaseView{
        fun goToMain()
    }

    interface Presenter {
        fun auth(login: String, password: String)
    }
}