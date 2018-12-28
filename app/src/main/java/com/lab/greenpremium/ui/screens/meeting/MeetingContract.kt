package com.lab.greenpremium.ui.screens.meeting

import com.lab.greenpremium.data.entity.Contact
import com.lab.greenpremium.ui.screens.base.BaseContract


interface MeetingContract {
    interface View : BaseContract.BaseView {
        fun initializeContactsCarousel(contacts: List<Contact>)
    }

    interface Presenter {
        fun onViewCreated()
        fun addMeeting(model: MeetingModel)
    }
}