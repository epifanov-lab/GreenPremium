package com.lab.greenpremium.ui.screens.main.plants.sub

import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.screens.base.BaseContract


interface PlantsSubContract {
    interface View : BaseContract.BaseView {
        fun initializeCatalog(products: List<Product>)
        fun notifyRecyclerDataChanged()
        fun goToDetails(product: Product)
    }

    interface Presenter {
        fun onViewCreated(sectionPosition: Int)
        fun onProductSelected(product: Product)
        fun onProductsRecyclerBottomReached(size: Int)
    }
}