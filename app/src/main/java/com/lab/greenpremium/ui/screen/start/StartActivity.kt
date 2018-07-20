package com.lab.greenpremium.ui.screen.start

import com.lab.greenpremium.R
import com.lab.greenpremium.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : BaseActivity() {

    override fun layoutResId(): Int {
        return R.layout.activity_start
    }

    override fun initializeDaggerComponent() {
        //ignore
    }

    override fun initViews() {
        button_auth.setOnClickListener { goToAuthScreen() }
    }

}

