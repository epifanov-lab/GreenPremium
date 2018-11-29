package com.lab.greenpremium.ui.screen.main.profile

import com.lab.greenpremium.data.Repository
import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.network.CallbackListener
import com.lab.greenpremium.utills.LogUtil
import javax.inject.Inject

class ProfilePresenter @Inject constructor(val view: ProfileContract.View) : ProfileContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        repository.updateContacts(object : CallbackListener {
            override fun doBefore() {
                view.showLoadingStub(false)
            }

            override fun doAfter() {
                view.showLoadingStub(true)
            }

            override fun onError(throwable: Throwable) {
                view.showError(throwable)
            }

            override fun onSuccess() {
                //view.initializeContactsCarousel(UserModel.contacts!!.managers)
                LogUtil.i("SUCCESS: ${UserModel.contacts}")
            }
        })
    }

}