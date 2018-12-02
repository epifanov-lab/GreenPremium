package com.lab.greenpremium.ui.screen.main.contacts

import com.lab.greenpremium.data.Repository
import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.network.CallbackListener
import javax.inject.Inject

class ContactsPresenter @Inject constructor(val view: ContactsContract.View) : ContactsContract.Presenter {

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
                view.initializeContactsCarousel(UserModel.contacts!!.getManagers(true))
            }
        })
    }

}