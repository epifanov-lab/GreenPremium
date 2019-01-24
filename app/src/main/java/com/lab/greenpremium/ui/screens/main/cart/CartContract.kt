package com.lab.greenpremium.ui.screens.main.cart

import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.screens.base.BaseContract


interface CartContract {
    interface View : BaseContract.BaseView {
        fun initializeCartProductsList(products: List<Product>?, isDemo: Boolean)
        fun updateTotalCost(total: Double)
        fun initializeServiceText(service_text: String)

        fun onBillRequestSuccess(message: String)

        fun setBillButtonEnabled(isEnabled: Boolean)
    }

    interface Presenter {
        fun onViewCreated()
        fun onCartUpdatedEvent()
        fun onClickBillRequest()
    }
}