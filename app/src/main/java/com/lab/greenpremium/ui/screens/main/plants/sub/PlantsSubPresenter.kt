package com.lab.greenpremium.ui.screens.main.plants.sub

import com.lab.greenpremium.data.CartModel
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repo.Repository
import java.io.Serializable
import javax.inject.Inject

class PlantsSubPresenter @Inject constructor(val view: PlantsSubContract.View) : PlantsSubContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    private var sectionId: Int = 0

    override fun onViewCreated(sectionPosition: Int) {
        updateSectionProducts(sectionPosition)
    }

    override fun onProductSelected(product: Product) {
        getProductDetails(product)
    }

    private fun updateSectionProducts(sectionPosition: Int) {
        CartModel.catalog?.sections?.let { sections ->
            sectionId = sections[sectionPosition].id
            repository.getSectionProductsList(sectionId, object : DefaultCallbackListener(view) {
                override fun onSuccess() {
                    sections[sectionPosition].products?.let { products ->
                        CartModel.syncCatalogWithCart()
                        this@PlantsSubPresenter.view.initializeCatalog(products)
                    }
                }
            })
        }
    }

    private fun getProductDetails(product: Product) {
        repository.getProductDetail(sectionId, product.id,
                object : DefaultCallbackListener(view) {
                    override fun onSuccess(item: Serializable?) {
                        item?.let {

                            val tempProduct = it as Product
                            tempProduct.isFavorite = product.isFavorite
                            tempProduct.quantity = product.quantity
                            this@PlantsSubPresenter.view.goToDetails(tempProduct)

                        } ?: (this@PlantsSubPresenter.view.showError(Throwable("Error while receiving product data")))
                    }
                })
    }

}