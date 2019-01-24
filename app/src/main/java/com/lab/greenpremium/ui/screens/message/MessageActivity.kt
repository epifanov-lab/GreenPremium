package com.lab.greenpremium.ui.screens.message

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import com.jakewharton.rxbinding2.widget.RxTextView
import com.lab.greenpremium.App
import com.lab.greenpremium.KEY_OBJECT
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.components.Listener
import com.lab.greenpremium.ui.screens.base.BaseActivity
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.activity_message.*

import javax.inject.Inject

class MessageActivity : BaseActivity(), MessageContract.View {

    @Inject
    internal lateinit var presenter: MessagePresenter

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

    @SuppressLint("CheckResult")
    override fun initViews() {
        button_back.setOnClickListener { finish() }
        presenter.onViewCreated(intent.extras.getSerializable(KEY_OBJECT) as MessageScreenType)

        presenter.initializeThemeInput(RxTextView.textChanges(input_subject).map { it.toString() })
        presenter.initializeMessageInput(RxTextView.textChanges(input_message.getTextView()).map { it.toString() })
        rating_bar.setOnRatingChangeListener { v, rating -> presenter.onRatingChanged(rating) }

        button_send.setOnClickListener { presenter.onClickSend() }
        setTouchAnimationShrink(button_send)
    }

    override fun initViewByType(type: MessageScreenType) {
        title_label.text = getString(type.titleResId)
        rating_bar.visibility = if (type.hasRatingBar) View.VISIBLE else View.GONE
        container_subject.visibility = if (type.hasSubjectInput) View.VISIBLE else View.GONE
        container_subject.visibility = if (type.hasSubjectInput) View.VISIBLE else View.GONE
        input_message.visibility = if (type.hasMessageInput) View.VISIBLE else View.GONE
        //file_photo.visibility = if (sectionId.hasPhotoAdding) View.VISIBLE else View.GONE
        //file_docs.visibility = if (sectionId.hasDocsAdding) View.VISIBLE else View.GONE
    }

    override fun onSentSuccess(messageResId: Int) {
        showDialogMessage(null, messageResId, object : Listener {
            override fun onEvent() {
                finishWithResult(Activity.RESULT_OK)
            }
        })
    }

    override fun setSendButtonEnabled(isEnabled: Boolean) {
        button_send.isEnabled = isEnabled
    }
}
