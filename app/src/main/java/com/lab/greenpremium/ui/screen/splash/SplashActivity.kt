package com.lab.greenpremium.ui.screen.splash

import android.os.Bundle
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        goToStartScreen()
    }
}
