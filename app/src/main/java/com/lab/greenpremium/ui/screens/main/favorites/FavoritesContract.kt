package com.lab.greenpremium.ui.screens.main.favorites

import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.screens.base.BaseContract


interface FavoritesContract {
    interface View : BaseContract.BaseView {
        fun initializeFavoritesList(favorites: List<Product>)
    }

    interface Presenter {
        fun onViewCreated()
        fun updateFavoritesList()
    }
}