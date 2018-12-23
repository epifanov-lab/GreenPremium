package com.lab.greenpremium.data.network

import com.lab.greenpremium.ui.screen.base.BaseContract
import java.io.Serializable

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
        // need to be overridden
    }

    override fun onSuccess(item: Serializable?) {
        // need to be overridden
    }
}

