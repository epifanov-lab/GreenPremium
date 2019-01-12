package com.lab.greenpremium.ui.screens.main

import com.lab.greenpremium.data.CartModel
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repo.Repository
import javax.inject.Inject

class MainPresenter @Inject constructor(val view: MainContract.View) : MainContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        updateCart()
    }

    override fun onCartChangedEvent(product: Product) {
        //TODO ON PRODUCTS ADDED/REMOVED
    }

    override fun updateCart() {
        repository.getCart(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                this@MainPresenter.view.updateCartIndicator(CartModel.products.size)
            }
        })
    }

}