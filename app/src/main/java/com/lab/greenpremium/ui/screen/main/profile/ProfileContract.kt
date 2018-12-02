package com.lab.greenpremium.ui.screen.main.profile

import com.lab.greenpremium.data.entity.Contact
import com.lab.greenpremium.ui.screen.base.BaseContract


interface ProfileContract {
    interface View : BaseContract.BaseView {
        fun showLoadingStub(show: Boolean)
        fun initializeContactsCarousel(contacts: List<Contact>)
    }

    interface Presenter {
        fun onViewCreated()
    }
}