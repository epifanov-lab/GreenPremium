package com.lab.greenpremium.ui.screen.start

import android.os.Bundle
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.base.BaseActivity

import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        button_auth.setOnClickListener { goToAuthScreen() }
    }
}

