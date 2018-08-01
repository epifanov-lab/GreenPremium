package com.lab.greenpremium.ui.dialog

import android.app.FragmentManager
import android.widget.NumberPicker
import com.lab.greenpremium.R
import kotlinx.android.synthetic.main.dialog_date_time_picker.view.*
import java.util.*

class DateTimePickerDialog : BaseDialogFragment() {

    val name = DateTimePickerDialog::class.simpleName
    var listener: DateTimePickerListener? = null
    val calendar = Calendar.getInstance()

    interface DateTimePickerListener {
        fun onClickAccept(date: Long)
    }

    companion object {
        fun show(fragmentManager: FragmentManager, listener: DateTimePickerListener) {
            val dialog = DateTimePickerDialog()
            dialog.listener = listener
            dialog.show(fragmentManager, DateTimePickerDialog::class.simpleName)
        }
    }

    override fun layoutResId(): Int {
        return R.layout.dialog_date_time_picker
    }

    override fun initViews() {
        dialogView.ok.setOnClickListener { listener?.onClickAccept(System.currentTimeMillis()).let { dismiss() } }
    }
}