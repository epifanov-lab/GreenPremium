package com.lab.greenpremium.ui.screens.main

import com.lab.greenpremium.data.CartModel
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repo.Repository
import com.lab.greenpremium.utills.eventbus.CartUpdatedEvent
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class MainPresenter @Inject constructor(val view: MainContract.View) : MainContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        updateCart()
    }

    override fun onProductQuantityChanged(product: Product) {
        repository.addToCart(product.getChosenOffer().product_id, product.quantity, object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                this@MainPresenter.view.updateCartIndicator(CartModel.cart.products.size)
                CartModel.syncCatalogWithCartByProduct(product)
                EventBus.getDefault().post(CartUpdatedEvent())
            }
        })
    }

    override fun updateCart() {
        repository.getCart(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                this@MainPresenter.view.updateCartIndicator(CartModel.cart.products.size)
            }
        })
    }

}