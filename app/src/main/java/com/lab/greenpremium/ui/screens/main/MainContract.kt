package com.lab.greenpremium.ui.screens.main

import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.screens.base.BaseContract


interface MainContract {
    interface View : BaseContract.BaseView {
        fun updateCartIndicator(count: Int)
        fun onLogout()
    }

    interface Presenter {
        fun onViewCreated()

        fun onProductQuantityChanged(product: Product)
        fun updateCart()
        fun updateFavoritesList()

        fun onClickLogout()
    }
}