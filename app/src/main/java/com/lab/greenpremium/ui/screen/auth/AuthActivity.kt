package com.lab.greenpremium.ui.screen.auth

import com.lab.greenpremium.*
import com.lab.greenpremium.ui.screen.base.BaseActivity
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

        button_back.setOnClickListener { finish() }
        button_auth.setOnClickListener { presenter.auth(input_login.text.toString(), input_password.text.toString()) }

        button_forgot_pass.setOnClickListener {
            if (DEBUG_MODE) { //todo исправить когда будешь реализовывать ссылки
                input_login.setText(TEST_USER_LOGIN)
                input_password.setText(TEST_USER_PASSWORD)
            }
        }

        setTouchAnimationShrink(button_auth)
    }

    override fun goToMain() {
        super.goToMainScreen()
    }
}
