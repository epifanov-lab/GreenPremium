package com.lab.greenpremium.ui.screen.main.contacts.meet

import android.support.v7.widget.LinearLayoutManager
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.base.BaseActivity
import com.lab.greenpremium.ui.screen.main.contacts.ContactsRecyclerAdapter
import com.lab.greenpremium.utills.getMockContactList
import kotlinx.android.synthetic.main.activity_meeting.*


class MeetingActivity : BaseActivity() {

    override fun layoutResId(): Int {
        return R.layout.activity_meeting
    }

    override fun initializeDaggerComponent() {
        //ignore
    }

    override fun initViews() {
        initializeContactsCarousel()

        button_back.setOnClickListener { finish() }
    }

    private fun initializeContactsCarousel() {
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = ContactsRecyclerAdapter(getMockContactList(), LinearLayoutManager.HORIZONTAL,
                this.resources?.getDimension(R.dimen.space_medium_2)?.toInt())
    }
}