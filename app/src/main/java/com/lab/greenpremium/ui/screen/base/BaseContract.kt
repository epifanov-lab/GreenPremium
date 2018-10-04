package com.lab.greenpremium.ui.screen.base


interface BaseContract {
    interface BaseView {
        fun showError(text: String? = null, textResId: Int? = null)
        fun showSnackbar(text: String? = null, textResId: Int? = null)
        fun showDialogMessage(text: String? = null, textResId: Int? = null)
        fun showLoadingDialog(show: Boolean = true)
    }

    interface BasePresenter {

    }
}