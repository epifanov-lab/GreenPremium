package com.lab.greenpremium.ui.components

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.lab.greenpremium.R
import kotlinx.android.synthetic.main.view_message.view.*

class MessageView : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_message, this, true)

        text.setOnFocusChangeListener { _, focused ->
            title.setTextColor(ContextCompat.getColor(context, if (focused) R.color.colorAccent else R.color.gray))
            text.background = ContextCompat.getDrawable(context, if (focused) R.drawable.bcg_message_active else R.drawable.bcg_message_passive)
        }
    }
}