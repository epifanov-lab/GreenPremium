package com.lab.greenpremium.ui.screen.auth

import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screen.base.BaseActivity
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : BaseActivity() {

    override fun layoutResId(): Int {
        return R.layout.activity_auth
    }

    override fun initializeDaggerComponent() {
        //ignore
    }

    override fun initViews() {
        button_back.setOnClickListener { finish() }
        button_auth.setOnClickListener { goToMainScreen() }

        setTouchAnimationShrink(button_auth)
    }
}
