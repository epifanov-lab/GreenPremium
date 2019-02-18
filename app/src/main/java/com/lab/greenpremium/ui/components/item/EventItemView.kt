package com.lab.greenpremium.ui.components.item

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Event
import kotlinx.android.synthetic.main.view_item_event.view.*


class EventItemView : RelativeLayout {

    var event: Event? = null
        set(value) {
            field = value
            updateView()
        }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_item_event, this, true)
    }

    private fun updateView() {
        text_schedule.text = event?.message
        text_date_time.text = event?.created
        event?.let { container_pdf.visibility = if (event?.file_path.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE }
    }

    fun setNum(num: Int) {
        text_num.text = String.format("%02d", num + 1)
    }

    fun hideLineConnector(hide: Boolean) {
        line_connection.visibility = if (hide) View.INVISIBLE else View.VISIBLE
    }

    fun setOnClickPdfListener(listener: OnClickListener) {
        container_pdf.setOnClickListener(listener)
    }

    fun setMargins(left: Int, top: Int, right: Int, bottom: Int) {
        val p = LinearLayout.LayoutParams(LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        p.setMargins(left, top, right, bottom)
        this.layoutParams = p
        this.requestLayout()
    }

}