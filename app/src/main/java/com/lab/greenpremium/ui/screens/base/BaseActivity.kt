package com.lab.greenpremium.ui.screens.base

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_LONG
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lab.greenpremium.*
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.components.Listener
import com.lab.greenpremium.ui.screens.auth.AuthActivity
import com.lab.greenpremium.ui.screens.calculator.CalcActivity
import com.lab.greenpremium.ui.screens.delivery.DeliveryActivity
import com.lab.greenpremium.ui.screens.gallery.GalleryActivity
import com.lab.greenpremium.ui.screens.main.MainActivity
import com.lab.greenpremium.ui.screens.message.MessageActivity
import com.lab.greenpremium.ui.screens.plant_detail.PlantDetailActivity
import com.lab.greenpremium.ui.screens.start.StartActivity
import com.lab.greenpremium.utills.getErrorMessage
import com.lab.greenpremium.utills.hideKeyboard
import java.io.Serializable
import java.util.ArrayList


abstract class BaseActivity : AppCompatActivity(), BaseContract.BaseView {

    protected abstract fun layoutResId(): Int

    protected abstract fun initializeDaggerComponent()

    protected abstract fun initViews()

    private val progressDialog: Dialog by lazy {
        AlertDialog.Builder(this).let {
            it.setView(R.layout.dialog_progress)
            it.setCancelable(false)
        }.create()
    }

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
        startActivityForResult(intent, KEY_RESULT_MESSAGE_SENT)
    }

    fun goToPlantDetailActivity(product: Product) {
        val intent = Intent(this, PlantDetailActivity::class.java)
        intent.putExtra(KEY_OBJECT, product)
        startActivity(intent)
    }

    fun goToCalcScreen() {
        startActivityForResult(Intent(this, CalcActivity::class.java), KEY_RESULT_CALCULATOR)
    }

    fun goToDeliveryScreen(order_id: Int?) {
        val intent = Intent(this, DeliveryActivity::class.java)
        intent.putExtra(KEY_OBJECT, order_id)
        startActivity(intent)
    }

    fun goToGalleryScreen(urls: ArrayList<String>, chosenImageNum: Int) {
        val intent = Intent(this, GalleryActivity::class.java)
        intent.putStringArrayListExtra(KEY_IMAGES_URLS_LIST, urls)
        intent.putExtra(KEY_CHOSEN_IMAGE_NUM, chosenImageNum)
        startActivity(intent)
    }

    protected fun swapFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun showError(throwable: Throwable) {
        showSnackbar(getErrorMessage(throwable))
    }

    override fun showSnackbar(text: String?, textResId: Int?) {
        hideKeyboard(this)
        val root = (this.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        text?.let { Snackbar.make(root, text, LENGTH_LONG).show() }
        textResId?.let { Snackbar.make(root, textResId, LENGTH_LONG).show() }
    }

    override fun showDialogMessage(text: String?, textResId: Int?, listener: Listener?) {
        val alertDialog = AlertDialog.Builder(this@BaseActivity).create()
        alertDialog.setTitle("Сообщение")

        text?.let { alertDialog.setMessage(text) }
        textResId?.let { alertDialog.setMessage(getString(textResId)) }

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, _ ->
            run {
                listener?.onEvent()
                dialog.dismiss()
            }
        }

        alertDialog.show()
    }

    fun showToast(text: String? = null, textResId: Int? = null) {
        text?.let { Toast.makeText(this, text, Toast.LENGTH_SHORT).show() }
        textResId?.let { Toast.makeText(this, textResId, Toast.LENGTH_SHORT).show() }
    }

    override fun showLoadingDialog(show: Boolean) {
        if (show) progressDialog.show()
        else progressDialog.dismiss()
    }

    override fun finishWithResult(result: Int) {
        setResult(result, Intent())
        finish()
    }

    override fun finishWithMessage(message: String?) {
        val intent = Intent()
        if (message != null) {
            intent.putExtra(KEY_OBJECT, message)
            setResult(Activity.RESULT_OK, intent)

        } else {
            setResult(Activity.RESULT_CANCELED, intent)
        }

        finish()
    }
}