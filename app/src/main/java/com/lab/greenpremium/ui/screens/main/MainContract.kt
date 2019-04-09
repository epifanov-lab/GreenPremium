package com.lab.greenpremium.ui.screens.main

import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.screens.base.BaseContract


interface MainContract {
    interface View : BaseContract.BaseView {
        fun updateCartIndicator(count: Int)
        fun onLogout()
    }

    interface Presenter {
        fun onViewStarted()

        fun onProductQuantityChanged(product: Product)
        fun updateCartAndFavorites()
        fun updateFavoritesList()

        fun onClickLogout()
    }
}