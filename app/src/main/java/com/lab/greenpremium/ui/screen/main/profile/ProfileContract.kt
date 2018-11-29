package com.lab.greenpremium.ui.screen.main.profile

import com.lab.greenpremium.data.entity.ManagerContact
import com.lab.greenpremium.ui.screen.base.BaseContract


interface ProfileContract {
    interface View : BaseContract.BaseView {
        fun showLoadingStub(show: Boolean)
        fun initializeContactsCarousel(contacts: List<ManagerContact>)
    }

    interface Presenter {
        fun onViewCreated()
    }
}