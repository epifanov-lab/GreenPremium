package com.lab.greenpremium.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import com.lab.greenpremium.R
import com.lab.greenpremium.data.network.ApiError
import com.lab.greenpremium.data.network.CallbackListener
import com.lab.greenpremium.utills.ValidationPatterns
import com.lab.greenpremium.utills.getScreenWidth
import com.lab.greenpremium.utills.hideKeyboard
import com.lab.greenpremium.utills.showKeyboard
import java.io.Serializable


class RestorePasswordDialog : BaseDialogFragment(), CallbackListener {

    interface RestorePasswordDialogListener {
        fun onClickSend(email: String)
    }

    var listener: RestorePasswordDialogListener? = null

    private val container_input: TextInputLayout by lazy { dialogView.findViewById<TextInputLayout>(R.id.container_input) }
    private val input_email: TextInputEditText by lazy { dialogView.findViewById<TextInputEditText>(R.id.input_email) }
    private val button_cancel: View by lazy { dialogView.findViewById<View>(R.id.button_cancel) }
    private val button_send: View by lazy { dialogView.findViewById<View>(R.id.button_send) }
    private val progress: View by lazy { dialogView.findViewById<View>(R.id.progress) }

    override fun layoutResId(): Int {
        return R.layout.dialog_restore_password
    }

    companion object {
        fun getInstance(): RestorePasswordDialog {
            return RestorePasswordDialog()
        }
    }

    fun show(context: Context) {
        this.show((context as Activity).fragmentManager, RestorePasswordDialog::class.simpleName)
        showKeyboard(context)
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout((getScreenWidth(activity) * 0.85f).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCanceledOnTouchOutside(false)
    }

    @SuppressLint("CheckResult")
    override fun initViews() {

        RxTextView.textChanges(input_email).subscribe { setError(null) }

        button_send.setOnClickListener {
            val text = input_email.text.toString()
            if (validate(text)) listener?.onClickSend(text)
        }

        button_cancel.setOnClickListener {
            dismissDialog()
        }
    }

    private fun showLoadingIndicator(show: Boolean) {
        progress.visibility = if (show) View.VISIBLE else View.INVISIBLE
        button_send.visibility = if (show) View.INVISIBLE else View.VISIBLE
    }

    private fun setError(error: String?) {
        container_input.error = error
    }

    private fun dismissDialog() {
        hideKeyboard(activity)
        dismiss()
    }

    private fun validate(text: String): Boolean {
        var result = false
        var error: String? = null

        if (text.isBlank()) error = resources.getString(R.string.error_empty_field_2)
        else if (!ValidationPatterns.EMAIL.validate(text)) error = resources.getString(R.string.error_wrong_email)
        else result = true

        setError(error)
        return result
    }

    //region Callbacks
    override fun doBefore() {
        showLoadingIndicator(true)
    }

    override fun doAfter() {
        showLoadingIndicator(false)
    }

    override fun onError(throwable: Throwable) {
        if (throwable is ApiError) setError(throwable.title)
        else setError(throwable.message)
    }

    override fun onSuccess() {
        dismissDialog()
    }

    override fun onSuccess(item: Serializable?) {
        //ignore
    }
    //endregion
}