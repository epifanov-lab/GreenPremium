package com.lab.greenpremium.ui.screen.main.contacts.meet

import com.lab.greenpremium.data.entity.Contact
import com.lab.greenpremium.ui.screen.base.BaseContract


interface MeetingContract {
    interface View : BaseContract.BaseView {
        fun initializeContactsCarousel(contacts: List<Contact>)
    }

    interface Presenter {
        fun onViewCreated()
        fun addMeeting(model: MeetingModel)
    }
}