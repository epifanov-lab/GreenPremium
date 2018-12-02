package com.lab.greenpremium.ui.screen.main.contacts.meet

import com.lab.greenpremium.data.Repository
import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.network.CallbackListener
import javax.inject.Inject

class MeetingPresenter @Inject constructor(val view: MeetingContract.View) : MeetingContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        repository.updateContacts(object : CallbackListener {
            override fun doBefore() {
                //ignore
            }

            override fun doAfter() {
                //ignore
            }

            override fun onError(throwable: Throwable) {
                view.showError(throwable)
            }

            override fun onSuccess() {
                view.initializeContactsCarousel(UserModel.contacts!!.getManagers())
            }
        })
    }

}