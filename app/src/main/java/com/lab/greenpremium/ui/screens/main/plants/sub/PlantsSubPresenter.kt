package com.lab.greenpremium.ui.screens.main.plants.sub

import com.lab.greenpremium.data.Repository
import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.data.network.DefaultCallbackListener
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
        UserModel.catalogSectionsResponse?.sections?.let { sections ->
            sectionId = sections[sectionPosition].id
            repository.getSectionProductsList(sectionId, object : DefaultCallbackListener(view) {
                override fun onSuccess() {
                    sections[sectionPosition].products?.let { products ->
                        this@PlantsSubPresenter.view.initializeCatalog(products)
                    }
                }
            })
        }
    }

    private fun getProductDetails(product: Product) {
        repository.getProductDetail(sectionId, product.id, object : DefaultCallbackListener(view) {
            override fun onSuccess(item: Serializable?) {
                item?.let { this@PlantsSubPresenter.view.goToDetails(it as Product) }
                        ?: (this@PlantsSubPresenter.view.showError(Throwable("Error while receiving product data")))
            }
        })
    }

}