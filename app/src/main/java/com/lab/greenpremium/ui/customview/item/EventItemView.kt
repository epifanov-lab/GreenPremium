package com.lab.greenpremium.ui.customview.item

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Event
import kotlinx.android.synthetic.main.view_item_event.view.*
import java.text.SimpleDateFormat
import java.util.*


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
        text_info.text = event?.info
        text_date_time.text = SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault()).format(event?.date)
        event?.let { container_pdf.visibility = if (event?.pdf!!) View.VISIBLE else View.INVISIBLE }
    }

    fun setNum(num: Int) {
        text_num.text = String.format("%02d", num + 1)
    }

    fun hideLineConnector(hide: Boolean) {
        line_connection.visibility = if (hide) View.INVISIBLE else View.VISIBLE
    }

    fun setMargins(left: Int, top: Int, right: Int, bottom: Int) {
        //TODO - make base ItemView
        val p = LinearLayout.LayoutParams(LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        p.setMargins(left, top, right, bottom)
        this.layoutParams = p
        this.requestLayout()
    }
}