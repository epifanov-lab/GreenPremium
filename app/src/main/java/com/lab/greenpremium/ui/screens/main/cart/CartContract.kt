package com.lab.greenpremium.ui.screens.main.cart

import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.screens.base.BaseContract


interface CartContract {
    interface View : BaseContract.BaseView {
        fun updateTotalCost(total: Double)
    }

    interface Presenter {
        fun onProductCountChanged(product: Product)
    }
}