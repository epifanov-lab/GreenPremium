package com.lab.greenpremium.ui.screens.splash

import com.lab.greenpremium.ui.screens.base.BaseContract


interface SplashContract {
    interface View : BaseContract.BaseView {
        fun goStartScreen()
        fun goMainScreen()
    }

    interface Presenter{
        fun onViewCreated()
    }
}