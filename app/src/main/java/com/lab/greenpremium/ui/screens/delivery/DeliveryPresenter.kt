package com.lab.greenpremium.ui.screens.delivery

import com.lab.greenpremium.data.repo.Repository
import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.entity.OrderRequest
import com.lab.greenpremium.data.network.DefaultCallbackListener
import javax.inject.Inject

class DeliveryPresenter @Inject constructor(val view: DeliveryContract.View) : DeliveryContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated(order_id: Int) {
        getOrderList(order_id)
    }

    override fun getOrderList(order_id: Int) {
        repository.getOrderList(OrderRequest(order_id), object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                UserModel.orderResponse?.let {
                    this@DeliveryPresenter.view.initializeProductsList(it.products, repository.isInDemoMode())
                }
            }
        })
    }
}