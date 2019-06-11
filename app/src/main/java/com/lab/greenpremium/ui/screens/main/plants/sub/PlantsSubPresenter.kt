package com.lab.greenpremium.ui.screens.main.plants.sub

import com.lab.greenpremium.PAGE_SIZE
import com.lab.greenpremium.data.CartModel
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repository.Repository
import java.io.Serializable
import javax.inject.Inject

class PlantsSubPresenter @Inject constructor(val view: PlantsSubContract.View) : PlantsSubContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    private var sectionPosition: Int = 0
    private var sectionId: Int = 0

    override fun onViewCreated(sectionPosition: Int) {
        updateSectionProducts(sectionPosition)
    }

    private fun updateSectionProducts(sectionPosition: Int) {
        this.sectionPosition = sectionPosition
        CartModel.catalog?.sections?.let { sections ->
            sectionId = sections[sectionPosition].id
            repository.getSectionProductsList(sectionId, object : DefaultCallbackListener(view) {
                override fun onSuccess() {
                    sections[sectionPosition].products?.let { products ->
                        CartModel.syncCatalogWithCart()
                        this@PlantsSubPresenter.view.initializeCatalog(products, repository.isInDemoMode())
                    }
                }
            })
        }
    }

    override fun onProductsRecyclerBottomReached(size: Int) {
        val page = (size / PAGE_SIZE) + 1
        if (size % PAGE_SIZE == 0 && page > 1) {
            CartModel.catalog?.sections?.let { sections ->
                repository.getSectionProductsListNextPage(sectionId, page, object : DefaultCallbackListener(view) {
                    override fun onSuccess() {
                        sections[sectionPosition].products?.let { products ->
                            CartModel.syncCatalogWithCart()
                            this@PlantsSubPresenter.view.notifyRecyclerDataChanged()
                        }
                    }
                })
            }
        }
    }

    override fun onProductSelected(product: Product) {
        getProductDetails(product)
    }

    private fun getProductDetails(product: Product) {

        repository.getProductDetail(sectionId, product.id,
                object : DefaultCallbackListener(view) {
                    override fun onSuccess(item: Serializable?) {
                        item?.let {
                            val tempProduct = it as Product
                            tempProduct.isFavorite = CartModel.isProductFavorite(product) ?: false
                            tempProduct.getSelectedOffer().sync(product.getSelectedOffer())
                            this@PlantsSubPresenter.view.goToDetails(tempProduct)

                        }
                                ?: (this@PlantsSubPresenter.view.showError(Throwable("Error while receiving product data")))
                    }
                })
    }
}