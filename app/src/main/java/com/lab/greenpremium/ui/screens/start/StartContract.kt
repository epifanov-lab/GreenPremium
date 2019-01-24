package com.lab.greenpremium.ui.screens.start

import com.lab.greenpremium.ui.screens.base.BaseContract


interface StartContract {
    interface View : BaseContract.BaseView {
        fun initializeGradientTitle()
        fun callToOffice()

        fun goToAuth()
        fun goToMain()

    }

    interface Presenter {
        fun onViewCreated()
        fun onClickAuth()
        fun onClickDemo()
        fun onClickSupport()
    }
}