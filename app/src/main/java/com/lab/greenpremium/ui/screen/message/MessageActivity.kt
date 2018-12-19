package com.lab.greenpremium.ui.screen.message

import android.view.View
import com.lab.greenpremium.App
import com.lab.greenpremium.KEY_OBJECT
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screen.base.BaseActivity
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.activity_message.*
import javax.inject.Inject

class MessageActivity : BaseActivity(), MessageContract.View {

    @Inject
    internal lateinit var presenter: MessagePresenter

    private lateinit var type: MessageScreenType

    override fun layoutResId(): Int {
        return R.layout.activity_message
    }

    override fun initializeDaggerComponent() {
        DaggerMessageComponent.builder()
                .appComponent((application as App).component)
                .messageModule(MessageModule(this))
                .build()
                .inject(this)
    }

    override fun initViews() {
        button_back.setOnClickListener { finish() }

        type = intent.extras.getSerializable(KEY_OBJECT) as MessageScreenType
        title_label.text = getString(type.titleResId)
        rating_bar.visibility = if (type.hasRatingBar) View.VISIBLE else View.GONE
        container_subject.visibility = if (type.hasSubjectInput) View.VISIBLE else View.GONE
        container_subject.visibility = if (type.hasSubjectInput) View.VISIBLE else View.GONE
        input_message.visibility = if (type.hasMessageInput) View.VISIBLE else View.GONE
        //file_photo.visibility = if (sectionId.hasPhotoAdding) View.VISIBLE else View.GONE
        //file_docs.visibility = if (sectionId.hasDocsAdding) View.VISIBLE else View.GONE

        setTouchAnimationShrink(button_send)
    }

}
