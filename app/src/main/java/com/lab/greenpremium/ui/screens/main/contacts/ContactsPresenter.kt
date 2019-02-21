package com.lab.greenpremium.ui.screens.main.contacts

import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.entity.Meeting
import com.lab.greenpremium.data.entity.MeetingStatusCode
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repository.Repository
import com.lab.greenpremium.utills.getTimestampFromDateString
import javax.inject.Inject

class ContactsPresenter @Inject constructor(val view: ContactsContract.View) : ContactsContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        view.setButtonScheduleEnabled(!repository.isInDemoMode())
        updateContacts()
        updateMeetingList()
    }

    override fun updateContacts() {
        repository.updateContacts(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                this@ContactsPresenter.view.initializeContactsCarousel(UserModel.contactsResponse!!.getManagers(true))
            }
        })
    }

    override fun updateMeetingList() {
        repository.updateMeetingsList(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                UserModel.meetingsListResponse?.meetings?.isNotEmpty().let {
                    val meeting = getFirstApprovedMeeting(UserModel.meetingsListResponse!!.meetings)
                    val timestamp = getTimestampFromDateString(meeting?.date)
                    this@ContactsPresenter.view.updateNextMeetingLabels(timestamp)
                    this@ContactsPresenter.view.setButtonScheduleEnabled(!repository.isInDemoMode())
                }
            }

            override fun onError(throwable: Throwable) {
                super.onError(throwable)
                this@ContactsPresenter.view.setButtonScheduleEnabled(!repository.isInDemoMode())
            }
        })
    }

    private fun getFirstApprovedMeeting(meetings: List<Meeting>): Meeting? {
        meetings.forEach {
            val isApproved = it.status.code == MeetingStatusCode.APPROVED.code
            if (isApproved) return it
        }
        return null
    }

}