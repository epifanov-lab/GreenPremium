package com.lab.greenpremium.ui.screen.main.profile

import com.lab.greenpremium.data.entity.Contact
import com.lab.greenpremium.data.entity.Event
import com.lab.greenpremium.ui.screen.base.BaseContract


interface ProfileContract {
    interface View : BaseContract.BaseView {
        fun showLoadingStub(show: Boolean)
        fun initializeContactsCarousel(contacts: List<Contact>)
        fun initializeServiceCostSection(payment: Double?)
        fun initializeEventsList(events: List<Event>)
        fun initializeOrdersSection(order_id: Int?, order_supply_date: String?)
        fun showNoEventsContainer()
    }

    interface Presenter {
        fun onViewCreated()
        fun updateEvents(forced: Boolean)
    }
}