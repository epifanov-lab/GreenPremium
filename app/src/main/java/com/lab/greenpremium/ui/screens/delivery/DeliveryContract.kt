package com.lab.greenpremium.ui.screens.delivery

import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.screens.base.BaseContract


interface DeliveryContract {
    interface View : BaseContract.BaseView {
        fun initializeProductsList(products: List<Product>, isDemo: Boolean)
    }

    interface Presenter {
        fun onViewCreated(order_id: Int)
        fun getOrderList(order_id: Int)
    }
}