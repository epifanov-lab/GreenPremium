package com.lab.greenpremium.ui.dialog


import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.lab.greenpremium.R


abstract class BaseDialogFragment : DialogFragment() {

    private val dialogBuilder: AlertDialog.Builder
        get() = AlertDialog.Builder(activity, R.style.Base_Theme_MaterialComponents_Dialog)

    protected abstract fun layoutResId(): Int
    protected abstract fun initViews()

    protected lateinit var dialogView: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = dialogBuilder
        dialogView = LayoutInflater.from(activity).inflate(layoutResId(), null, false)
        builder.setView(dialogView)
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout(WRAP_CONTENT, WRAP_CONTENT)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCanceledOnTouchOutside(true)
    }

    override fun onResume() {
        super.onResume()
        initViews()
    }
}