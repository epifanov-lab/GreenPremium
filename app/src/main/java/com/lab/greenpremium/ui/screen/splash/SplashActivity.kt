package com.lab.greenpremium.ui.screen.splash

import com.lab.greenpremium.R
import com.lab.greenpremium.ui.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun layoutResId(): Int {
        return R.layout.activity_splash
    }

    override fun initializeDaggerComponent() {
        //ignore
    }

    override fun initViews() {
        goToStartScreen()
    }

}
