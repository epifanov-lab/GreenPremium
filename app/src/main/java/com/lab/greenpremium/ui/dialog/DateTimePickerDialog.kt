package com.lab.greenpremium.ui.dialog

import android.support.v7.app.AlertDialog
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.base.BaseDialogFragment


class DateTimePickerDialog : BaseDialogFragment() {

    override val dialogBuilder: AlertDialog.Builder
        get() =  AlertDialog.Builder(activity, R.style.TransparentDialogFragment)

    override fun layoutResId(): Int {
        return R.layout.dialog_date_titme_picker
    }

    override fun initViews() {
        //ignore
    }
}