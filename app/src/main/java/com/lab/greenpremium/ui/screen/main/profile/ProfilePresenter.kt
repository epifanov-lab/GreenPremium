package com.lab.greenpremium.ui.screen.main.profile

import com.lab.greenpremium.data.Repository
import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.network.DefaultCallbackListener
import javax.inject.Inject

class ProfilePresenter @Inject constructor(val view: ProfileContract.View) : ProfileContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

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
                else this@ProfilePresenter.view.showNoEventsContainer()
            }
        })
    }

}