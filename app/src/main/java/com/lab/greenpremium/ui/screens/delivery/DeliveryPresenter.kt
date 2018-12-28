package com.lab.greenpremium.ui.screens.delivery

import com.lab.greenpremium.data.Repository
import javax.inject.Inject

class DeliveryPresenter @Inject constructor(val view: DeliveryContract.View) : DeliveryContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

}