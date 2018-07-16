package com.lab.greenpremium.ui.base

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.lab.greenpremium.ui.screen.auth.AuthActivity
import com.lab.greenpremium.ui.screen.main.MainActivity
import com.lab.greenpremium.ui.screen.start.StartActivity


abstract class BaseActivity : AppCompatActivity() {

    protected fun goToStartScreen() {
        startActivity(Intent(this, StartActivity::class.java))
    }

    protected fun goToAuthScreen() {
        startActivity(Intent(this, AuthActivity::class.java))
    }

    protected fun goToMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
    }

}