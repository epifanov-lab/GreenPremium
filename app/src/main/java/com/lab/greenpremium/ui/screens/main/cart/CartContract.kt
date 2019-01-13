package com.lab.greenpremium.ui.screens.main.cart

import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.screens.base.BaseContract


interface CartContract {
    interface View : BaseContract.BaseView {
        fun initializeCartProductsList(products: List<Product>?)
        fun updateTotalCost(total: Double)
        fun onBillRequestSuccess(message: String)
    }

    interface Presenter {
        fun onViewCreated()
        fun onCartUpdatedEvent()
        fun onClickBillRequest()
    }
}