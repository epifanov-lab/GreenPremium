package com.lab.greenpremium.ui.screens.main

import android.os.Handler
import com.lab.greenpremium.data.CartModel
import com.lab.greenpremium.data.CartUpdatedEvent
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repository.Repository
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class MainPresenter @Inject constructor(val view: MainContract.View) : MainContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewStarted() {
        updateCartAndFavorites()
    }

    override fun onProductQuantityChanged(product: Product) {
        repository.addToCart(product.getSelectedOffer().product_id, product.getSelectedOffer().quantity, object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                CartModel.cart?.let {
                    this@MainPresenter.view.updateCartIndicator(CartModel.cart!!.total_quantity)
                }

                CartModel.syncCatalogWithCartByProduct(product)
                EventBus.getDefault().post(CartUpdatedEvent())
            }
        })
    }

    override fun updateCartAndFavorites() {
        repository.getCart(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                CartModel.cart?.let {
                    CartModel.syncCatalogWithCart()
                    this@MainPresenter.view.updateCartIndicator(CartModel.cart!!.total_quantity) }

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