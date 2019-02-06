package com.lab.greenpremium.ui.screens.main.profile

import com.lab.greenpremium.PAGE_SIZE
import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repo.Repository
import javax.inject.Inject

class ProfilePresenter @Inject constructor(val view: ProfileContract.View) : ProfileContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    private var isEventsPaginationEnabled = true

    override fun onViewCreated() {
        updateObjectInfo()
        updateEvents(false)
    }

    private fun updateObjectInfo() {
        repository.updateObjectsInfo(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                val info = UserModel.objectInfoResponse

                info?.let {

                    val biologists = info.biologists
                    biologists?.let {
                        if (biologists.isNotEmpty()) this@ProfilePresenter.view.initializeContactsCarousel(biologists)
                        else updateContacts()
                    }

                    this@ProfilePresenter.view.initializeServiceCostSection(info.payment)
                    this@ProfilePresenter.view.initializeOrdersSection(info.order_id, info.order_supply_date)
                    this@ProfilePresenter.view.showNoOrdersContainer(info.order_id == null)
                }
            }
        })
    }

    private fun updateContacts() {
        repository.updateContacts(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                val contacts = UserModel.contactsResponse!!.getManagers()
                if (contacts.isNotEmpty()) {
                    this@ProfilePresenter.view.initializeContactsCarousel(contacts.subList(0, 1))
                }
            }
        })
    }

    override fun updateEvents(forced: Boolean) {
        repository.updateEvents(forced, object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                val events = UserModel.eventsResponse!!.events
                if (events.isNotEmpty()) this@ProfilePresenter.view.initializeEventsList(events)
            }
        })
    }

    override fun onClickEventPdf(file_path: String) {
        view.openUrlInBrowser(file_path)
    }

    override fun onEventRecyclerBottomReached(size: Int) {
        val events = UserModel.eventsResponse?.events
        if (isEventsPaginationEnabled && events != null) {
            val page = (events.size / PAGE_SIZE) + 1
            repository.getEventsNextPage(page, object : DefaultCallbackListener(view) {
                override fun onSuccess() {
                    this@ProfilePresenter.view.notifyEventsRecyclerDataChanged()
                }
            })
        }
    }

    override fun onEventsPaginationStateChanged(enabled: Boolean) {
        isEventsPaginationEnabled = enabled
    }
}