package com.lab.greenpremium.ui.screens.main.contacts

import com.lab.greenpremium.data.entity.Contact
import com.lab.greenpremium.ui.screens.base.BaseContract


interface ContactsContract {
    interface View : BaseContract.BaseView {
        fun initializeContactsCarousel(contacts: List<Contact>)
        fun updateNextMeetingLabels(timestamp: Long?)
    }

    interface Presenter {
        fun onViewCreated()
        fun updateContacts()
        fun updateMeetingList()
    }
}