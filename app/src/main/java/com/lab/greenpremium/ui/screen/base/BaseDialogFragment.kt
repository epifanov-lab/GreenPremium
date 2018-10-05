package com.lab.greenpremium.ui.screen.base

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View


abstract class BaseDialogFragment: DialogFragment(){

    protected abstract fun getDialogBuilder(): AlertDialog.Builder
    protected abstract fun layoutResId(): Int
    protected abstract fun initViews()

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        return prepareDialog()
    }

    private fun prepareDialog(): Dialog {
        val builder = getDialogBuilder()
        builder.setView(inflateView())
        return builder.create()
    }


    protected fun inflateView(): View {
        val view = LayoutInflater.from(activity).inflate(layoutResId(), null, false)
        initViews()
        return view
    }

}