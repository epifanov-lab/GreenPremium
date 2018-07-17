package com.lab.greenpremium.ui.screen.auth

import android.os.Bundle
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.base.BaseActivity

import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        button_back.setOnClickListener { finish() }
        button_auth.setOnClickListener { goToMainScreen() }
    }
}
