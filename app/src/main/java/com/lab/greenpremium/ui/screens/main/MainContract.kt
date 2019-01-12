package com.lab.greenpremium.ui.screens.main

import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.screens.base.BaseContract


interface MainContract {
    interface View : BaseContract.BaseView {
        fun updateCartIndicator(count: Int)
    }

    interface Presenter {
        fun onViewCreated()

        fun onCartChangedEvent(product: Product)
        fun updateCart()
    }
}