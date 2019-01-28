package com.lab.greenpremium.ui.screens.main

import android.os.Handler
import com.lab.greenpremium.data.CartModel
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repo.Repository
import com.lab.greenpremium.data.CartUpdatedEvent
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class MainPresenter @Inject constructor(val view: MainContract.View) : MainContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        updateCart()
    }

    override fun onProductQuantityChanged(product: Product) {
        repository.addToCart(product.getSelectedOffer().product_id, product.quantity, object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                CartModel.cart?.let {
                    this@MainPresenter.view.updateCartIndicator(CartModel.cart!!.products.size)
                }

                CartModel.syncCatalogWithCartByProduct(product)
                EventBus.getDefault().post(CartUpdatedEvent())
            }
        })
    }

    override fun updateCart() {
        repository.getCart(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                CartModel.cart?.let {
                    this@MainPresenter.view.updateCartIndicator(CartModel.cart!!.products.size) }

                Handler().post {
                    // TODO сделать спайанный последовательный вызов
                    updateFavoritesList()
                }
            }
        })
    }

    override fun updateFavoritesList() {
        repository.getFavorites(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                CartModel.syncFavoritesWithCart()
                CartModel.syncCatalogWithFavorites()
            }
        })
    }

    override fun onClickLogout() {
        repository.logout()
        view.onLogout()
    }
}