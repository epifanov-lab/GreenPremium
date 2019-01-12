package com.lab.greenpremium.ui.screens.main.cart

import com.lab.greenpremium.data.entity.Product
import javax.inject.Inject

class CartPresenter @Inject constructor(val view: CartContract.View) : CartContract.Presenter {

    override fun onProductCountChanged(product: Product) {
        //todo update total cost
    }
}