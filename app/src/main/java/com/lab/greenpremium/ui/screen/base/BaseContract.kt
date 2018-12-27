package com.lab.greenpremium.ui.screen.base


interface BaseContract {
    interface BaseView {
        fun showError(throwable: Throwable)
        fun showSnackbar(text: String? = null, textResId: Int? = null)
        fun showDialogMessage(text: String? = null, textResId: Int? = null)
        fun showLoadingDialog(show: Boolean = true)
        fun finishWithMessage(message: String?)
    }

    interface BasePresenter
}