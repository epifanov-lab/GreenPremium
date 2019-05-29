package com.lab.greenpremium.ui.screens.main.favorites

import com.lab.greenpremium.data.CartModel
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repository.Repository
import java.io.Serializable
import javax.inject.Inject

class FavoritesPresenter @Inject constructor(val view: FavoritesContract.View) : FavoritesContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewResumed() {
        updateFavoritesList()
    }

    override fun updateFavoritesList() {
        repository.getFavorites(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                CartModel.syncFavoritesWithCart()
                CartModel.syncCatalogWithFavorites()
                CartModel.favorites?.let {
                    this@FavoritesPresenter.view.initializeFavoritesList(CartModel.favorites!!.products, repository.isInDemoMode())
                }
            }
        })
    }

    override fun onProductSelected(product: Product) {
        getProductDetails(product)
    }

    private fun getProductDetails(product: Product) {
        repository.getProductDetail(null, product.id,
                object : DefaultCallbackListener(view) {
                    override fun onSuccess(item: Serializable?) {
                        item?.let {

                            val tempProduct = it as Product
                            tempProduct.getSelectedOffer().sync(product.getSelectedOffer())
                            tempProduct.isFavorite = true
                            this@FavoritesPresenter.view.goToDetails(tempProduct)

                        }
                                ?: (this@FavoritesPresenter.view.showError(Throwable("Error while receiving product data")))
                    }
                })
    }
}