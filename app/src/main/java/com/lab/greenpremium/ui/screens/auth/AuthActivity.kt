package com.lab.greenpremium.ui.screens.auth

import com.jakewharton.rxbinding2.widget.RxTextView
import com.lab.greenpremium.*
import com.lab.greenpremium.ui.screens.base.BaseActivity
import com.lab.greenpremium.utills.LogUtil
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : BaseActivity(), AuthContract.View {

    @Inject
    internal lateinit var presenter: AuthPresenter

    override fun layoutResId(): Int {
        return R.layout.activity_auth
    }

    override fun initializeDaggerComponent() {
        DaggerAuthComponent.builder()
                .appComponent((application as App).component)
                .authModule(AuthModule(this))
                .build()
                .inject(this)
    }

    override fun initViews() {
        presenter.initializeDataInput(
                RxTextView.textChanges(input_login).map { it.toString() },
                RxTextView.textChanges(input_password).map { it.toString() }
        )

        button_auth.setOnClickListener { presenter.validateDataAndProceedAuth() }

        button_forgot_pass.setOnClickListener {
            LogUtil.e("BuildConfig.DEBUG ${BuildConfig.DEBUG}")

            if (BuildConfig.DEBUG) {
                input_login.setText(TEST_USER_LOGIN)
                input_password.setText(TEST_USER_PASSWORD)
            }
        }

        button_back.setOnClickListener { finish() }

        setTouchAnimationShrink(button_auth)
    }

    override fun setLoginInputError(textResId: Int?, formatStr: Int?) {
        container_login.error = getErrorText(textResId, formatStr)
        input_login.requestFocus()
    }

    override fun setPasswordInputError(textResId: Int?, formatStr: Int?) {
        container_password.error = getErrorText(textResId, formatStr)
        input_password.requestFocus()
    }

    private fun getErrorText(textResId: Int?, formatStr: Int?): CharSequence? {
        return when {
            textResId != null && formatStr == null -> getString(textResId)
            textResId != null && formatStr != null -> getString(textResId, getString(formatStr))
            else -> null
        }
    }

    override fun goToMain() {
        super.goToMainScreen()
    }
}
