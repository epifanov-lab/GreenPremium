package com.lab.greenpremium.ui.screen.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screen.main.MainActivity
import com.lab.greenpremium.ui.screen.start.StartActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        goToStartScreen()
    }

    fun goToStartScreen() {
        startActivity(Intent(this, StartActivity::class.java))
    }

    fun goToMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
