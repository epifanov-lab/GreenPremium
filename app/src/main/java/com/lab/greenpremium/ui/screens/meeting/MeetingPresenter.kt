package com.lab.greenpremium.ui.screens.meeting

import com.lab.greenpremium.data.repo.Repository
import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.utills.getFormattedDateString
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MeetingPresenter @Inject constructor(val view: MeetingContract.View) : MeetingContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        updateContacts()
    }

    private fun updateContacts() {
        repository.updateContacts(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                val contacts = UserModel.contactsResponse
                contacts?.let {
                    this@MeetingPresenter.view.initializeContactsCarousel(contacts.getManagersAvailableForMeeting())
                }
            }
        })
    }

    override fun addMeeting(model: MeetingModel) {
        val manager_id = UserModel.contactsResponse!!.getManagers()[model.pickedContactPos].id
        val date = getFormattedDateString(model.pickedTime)
        repository.addMeeting(manager_id, date, object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                this@MeetingPresenter.view.finishWithMessage(
                        "Встреча с ${UserModel.contactsResponse!!.getManagers()[model.pickedContactPos].name}" +
                                " назначена на ${SimpleDateFormat("dd MMMM HH:mm", Locale("ru")).format(model.pickedTime)}"
                )
            }
        })
    }
}