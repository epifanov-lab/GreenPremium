package com.lab.greenpremium.ui.dialog

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.view.Gravity
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.lab.greenpremium.CLICK_ACTION_THRESHOLD
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Offer
import com.lab.greenpremium.utills.LogUtil


class RadioButtonPickerDialog<T> : BaseDialogFragment() {

    var items: List<T>? = null
    var listener: PickerListener? = null
    var defIndex: Int? = null

    interface PickerListener {
        fun <T> onItemPicked(index: Int, item: T)
    }

    override fun layoutResId(): Int {
        return R.layout.dialog_radio_group
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout(resources.getDimensionPixelSize(R.dimen.dialog_pick_height_width),
                ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setGravity(Gravity.START or Gravity.BOTTOM)
        dialog.setCanceledOnTouchOutside(true)
    }

    override fun initViews() {

        if (items == null) {
            LogUtil.e("Picker dialog error: list is empty!")
            dismiss()
            return
        }

        val group = dialogView.findViewById<RadioGroup>(R.id.radio_button_group)

        items?.let {
            for ((index, item) in items!!.withIndex()) {
                val rbView = activity.layoutInflater.inflate(R.layout.dialog_picker_radiobutton, null)
                val button = rbView.findViewById<RadioButton>(R.id.radio_button)
                button.id = index + 100
                button.text = getItemTitle(item)

                button.setOnCheckedChangeListener { button, checked ->
                    button.setButtonDrawable(if (checked) R.drawable.ic_circle_checked else R.drawable.ic_circle_unchecked)
                    if (checked) {
                        listener?.onItemPicked(index, item)
                        Handler().postDelayed({ this@RadioButtonPickerDialog.dismiss() },
                                CLICK_ACTION_THRESHOLD.toLong())
                    }
                }

                group.addView(button)
            }


        }

    }

    private fun getItemTitle(item: T): String {
        var result: String = item.toString()

        when (item) {
            is Offer -> result = (item as Offer).height.value
        }

        return result
    }

    companion object {
        fun <T> show(context: Context, items: List<T>, defIndex: Int = 0, listener: PickerListener) {
            val dialog = RadioButtonPickerDialog<T>()
            dialog.items = items
            dialog.listener = listener
            dialog.defIndex = defIndex
            dialog.show((context as Activity).fragmentManager, RadioButtonPickerDialog::class.simpleName)
        }
    }
}