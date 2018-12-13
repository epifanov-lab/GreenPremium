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
        updateEvents()
    }

    private fun updateObjectInfo() {
        repository.updateObjectsInfo(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                val biologists = UserModel.objectInfo!!.biologists
                if (biologists.isNotEmpty()) this@ProfilePresenter.view.initializeContactsCarousel(biologists)
                else updateContacts()
            }
        })
    }

    private fun updateContacts() {
        repository.updateContacts(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                val contacts = UserModel.contacts!!.getManagers()
                if (contacts.isNotEmpty()) {
                    this@ProfilePresenter.view.initializeContactsCarousel(contacts.subList(0, 1))
                }
            }
        })
    }

    private fun updateEvents() {
        repository.updateEvents(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                val events = UserModel.eventsData!!.events
                if (events.isNotEmpty()) this@ProfilePresenter.view.initializeEventsList(events)
                else this@ProfilePresenter.view.showNoEventsContainer()
            }
        })
    }

}