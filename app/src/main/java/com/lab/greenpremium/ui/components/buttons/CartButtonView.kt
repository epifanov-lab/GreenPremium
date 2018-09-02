package com.lab.greenpremium.ui.components.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.lab.greenpremium.R
import kotlinx.android.synthetic.main.view_cart_button.view.*


class CartButtonView : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_cart_button, this, true)
        updateIndicator(0)
    }

    fun updateIndicator(count: Int) {
        indicator.visibility = if (count > 0) View.VISIBLE else View.INVISIBLE
        indicator.text = count.toString()
    }

    fun setImageResource(resId: Int) {
        icon.setImageResource(resId)
    }

    override fun setOnClickListener(onClickListener: OnClickListener) {
        super.setOnClickListener(onClickListener)
        container_cart.setOnClickListener(onClickListener)
    }
}