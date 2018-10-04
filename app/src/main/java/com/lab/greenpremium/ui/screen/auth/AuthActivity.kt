package com.lab.greenpremium.ui.screen.auth

import com.lab.greenpremium.App
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screen.base.BaseActivity
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : BaseActivity(), AuthContract.View {

    @Inject internal lateinit var presenter: AuthPresenter

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
        button_auth.setOnClickListener{ presenter.auth(input_login.text.toString(), input_password.text.toString()) }

        setTouchAnimationShrink(button_auth)
    }
}
