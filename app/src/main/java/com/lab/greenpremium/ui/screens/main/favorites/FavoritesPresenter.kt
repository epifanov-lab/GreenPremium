package com.lab.greenpremium.ui.screens.main.favorites

import com.lab.greenpremium.data.CartModel
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repository.Repository
import javax.inject.Inject

class FavoritesPresenter @Inject constructor(val view: FavoritesContract.View) : FavoritesContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        updateFavoritesList()
    }

    override fun updateFavoritesList() {
        repository.getFavorites(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                CartModel.syncFavoritesWithCart()
                CartModel.syncCatalogWithFavorites()
                CartModel.favorites?.let {
                    this@FavoritesPresenter.view.initializeFavoritesList(CartModel.favorites!!, repository.isInDemoMode())
                }
            }
        })
    }
}