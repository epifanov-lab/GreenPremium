package com.lab.greenpremium.ui.screen.base

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_LONG
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screen.auth.AuthActivity
import com.lab.greenpremium.ui.screen.main.MainActivity
import com.lab.greenpremium.ui.screen.start.StartActivity


abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun layoutResId(): Int

    protected abstract fun initializeDaggerComponent()

    protected abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeDaggerComponent()
        setContentView(layoutResId())
        initViews()
    }

    protected fun goToStartScreen() {
        startActivity(Intent(this, StartActivity::class.java))
        finish()
    }

    protected fun goToAuthScreen() {
        startActivity(Intent(this, AuthActivity::class.java))
    }

    protected fun goToMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    protected fun swapFragment(fragment : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun showSnackbar(text: String) {
        val root = (this.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        val snackBar = Snackbar.make(root, text, LENGTH_LONG)
        snackBar.show()
    }

    fun showSnackbar(textResId: Int) {
        showSnackbar(getString(textResId))
    }
}