package com.lab.greenpremium.ui.screens.main.profile

import com.lab.greenpremium.data.entity.Contact
import com.lab.greenpremium.data.entity.Event
import com.lab.greenpremium.ui.screens.base.BaseContract


interface ProfileContract {
    interface View : BaseContract.BaseView {
        fun initializeContactsCarousel(contacts: List<Contact>)
        fun initializeServiceCostSection(payment: Double?)
        fun initializeEventsList(events: List<Event>)
        fun notifyEventsRecyclerDataChanged()

        fun initializeOrdersSection(order_id: Int?, order_supply_date: String?)
        fun showNoOrdersContainer(show: Boolean)

        fun goToServiceCalculatorScreen()
        fun goToDeliveryScreen(order_id: Int?)
        fun openUrlInBrowser(url: String)
    }

    interface Presenter {
        fun onViewCreated()
        fun updateEvents(forced: Boolean)
        fun onClickEventPdf(file_path: String)
        fun onEventRecyclerBottomReached(size: Int)
        fun onEventsPaginationStateChanged(enable: Boolean)
    }
}