package com.lab.greenpremium.ui.screen.main.contacts

import com.lab.greenpremium.data.Repository
import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.entity.Meeting
import com.lab.greenpremium.data.entity.MeetingStatusCode
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.utills.getTimestampFromDateString
import javax.inject.Inject

class ContactsPresenter @Inject constructor(val view: ContactsContract.View) : ContactsContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        updateContacts()
        updateMeetingList()
    }

    override fun updateContacts() {
        repository.updateContacts(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                this@ContactsPresenter.view.initializeContactsCarousel(UserModel.contacts!!.getManagers(true))
            }
        })
    }

    override fun updateMeetingList() {
        repository.updateMeetingsList(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                UserModel.meetingsListData?.meetings?.isNotEmpty().let {
                    val meeting = getFirstApprovedMeeting(UserModel.meetingsListData!!.meetings)
                    val timestamp = getTimestampFromDateString(meeting?.date)
                    this@ContactsPresenter.view.updateNextMeetingLabels(timestamp)
                }
            }
        })
    }

    private fun getFirstApprovedMeeting(meetings: List<Meeting>): Meeting? {
        meetings.forEach{
            if (it.status.code.equals(MeetingStatusCode.APPROVED)) return it
        }
        return null
    }

}