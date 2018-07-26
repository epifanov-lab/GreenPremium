package com.lab.greenpremium.ui.customview.item

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Contact
import kotlinx.android.synthetic.main.view_item_contact.view.*


class ContactItemView : RelativeLayout {

    var contact: Contact? = null
        set(value) {
            field = value
            updateView()
        }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        LayoutInflater.from(context).inflate(R.layout.view_item_contact, this, true)

        button_call.setOnClickListener {
            contact?.phone?.let {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$it")
                context.startActivity(intent)
            }
        }

        button_mail.setOnClickListener {
            contact?.mail?.let {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", it, null))
                context.startActivity(intent)
            }
        }
    }

    private fun updateView() {
        text_name.text = contact?.name
        text_position.text = contact?.position
        text_phone.text = contact?.phone.also { if (contact?.phone == null) button_call.visibility = GONE }
        text_mail.text = contact?.mail.also { if (contact?.mail == null) button_mail.visibility = GONE }
        text_info.text = contact?.info
    }

    fun setMargins(left: Int, top: Int, right: Int, bottom: Int) {
        val p = LinearLayout.LayoutParams(LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        p.setMargins(left, top, right, bottom)
        this.layoutParams = p
        this.requestLayout()
    }
}