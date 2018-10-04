package com.lab.greenpremium.ui.screen.base

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_LONG
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lab.greenpremium.KEY_OBJECT
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screen.auth.AuthActivity
import com.lab.greenpremium.ui.screen.main.MainActivity
import com.lab.greenpremium.ui.screen.message.MessageActivity
import com.lab.greenpremium.ui.screen.plant_detail.PlantDetailActivity
import com.lab.greenpremium.ui.screen.start.StartActivity
import java.io.Serializable


abstract class BaseActivity : AppCompatActivity(), BaseContract.BaseView {

    protected abstract fun layoutResId(): Int

    protected abstract fun initializeDaggerComponent()

    protected abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId())
        initializeDaggerComponent()
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

    protected fun goToMessageScreen(messageScreenType: Serializable) {
        val intent = Intent(this, MessageActivity::class.java)
        intent.putExtra(KEY_OBJECT, messageScreenType)
        startActivity(intent)
    }

    fun goToPlantDetailActivity(plantIndex: Int) {
        val intent = Intent(this, PlantDetailActivity::class.java)
        intent.putExtra(KEY_OBJECT, plantIndex)
        startActivity(intent)
    }


    protected fun swapFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun showError(text: String?, textResId: Int?) {
        //todo implement
    }

    override fun showSnackbar(text: String?, textResId: Int?) {
        val root = (this.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        text?.let { Snackbar.make(root, text, LENGTH_LONG).show() }
        textResId?.let { Snackbar.make(root, textResId, LENGTH_LONG).show() }
    }

    override fun showDialogMessage(text: String?, textResId: Int?) {
        //todo implement
    }

    override fun showLoadingDialog(show: Boolean) {
        //todo implement
    }

    fun showToast(text: String? = null, textResId: Int? = null) {
        text?.let { Toast.makeText(this, text, Toast.LENGTH_SHORT).show() }
        textResId?.let { Toast.makeText(this, textResId, Toast.LENGTH_SHORT).show() }
    }
}