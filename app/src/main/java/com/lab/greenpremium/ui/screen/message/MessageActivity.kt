package com.lab.greenpremium.ui.screen.message

import android.view.View
import com.lab.greenpremium.R
import com.lab.greenpremium.SCREEN_KEY
import com.lab.greenpremium.ui.screen.base.BaseActivity
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : BaseActivity() {

    private lateinit var type: MessageScreenType

    override fun layoutResId(): Int {
        return R.layout.activity_message
    }

    override fun initializeDaggerComponent() {
        //ignore
    }

    override fun initViews() {
        button_back.setOnClickListener { finish() }

        type = intent.extras.getSerializable(SCREEN_KEY) as MessageScreenType
        title_label.text = getString(type.titleResId)
        rating_bar.visibility = if (type.hasRatingBar) View.VISIBLE else View.GONE
        container_subject.visibility = if (type.hasSubjectInput) View.VISIBLE else View.GONE
        container_subject.visibility = if (type.hasSubjectInput) View.VISIBLE else View.GONE
        input_message.visibility = if (type.hasMessageInput) View.VISIBLE else View.GONE
        //file_photo.visibility = if (type.hasPhotoAdding) View.VISIBLE else View.GONE
        //file_docs.visibility = if (type.hasDocsAdding) View.VISIBLE else View.GONE
    }
}
