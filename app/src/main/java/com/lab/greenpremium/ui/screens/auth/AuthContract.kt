package com.lab.greenpremium.ui.screens.auth

import android.support.annotation.StringRes
import com.lab.greenpremium.ui.screens.base.BaseContract
import io.reactivex.Observable

interface AuthContract {
    interface View : BaseContract.BaseView {
        fun setLoginInputError(@StringRes textResId: Int?, @StringRes formatStr: Int? = null)
        fun setPasswordInputError(@StringRes textResId: Int?, @StringRes formatStr: Int? = null)
        fun goToMain()
    }

    interface Presenter {
        fun initializeDataInput(login: Observable<String>, password: Observable<String>)
        fun validateDataAndProceedAuth()
    }
}