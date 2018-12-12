package com.lab.greenpremium.data.network

import com.lab.greenpremium.ui.screen.base.BaseContract

open class DefaultCallbackListener(val view: BaseContract.BaseView) : CallbackListener {

    override fun doBefore() {
        view.showLoadingDialog(true)
    }

    override fun doAfter() {
        view.showLoadingDialog(false)
    }

    override fun onError(throwable: Throwable) {
        view.showError(throwable)
    }

    override fun onSuccess() {
        // need to be overridden!
    }
}

