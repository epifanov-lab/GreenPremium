package com.lab.greenpremium.ui.screen.message

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
        type = intent.extras.getSerializable(SCREEN_KEY) as MessageScreenType
        title_label.text = getString(type.titleResId)

        button_back.setOnClickListener { finish() }
    }
}
