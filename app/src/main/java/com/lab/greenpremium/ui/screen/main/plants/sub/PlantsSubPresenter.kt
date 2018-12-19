package com.lab.greenpremium.ui.screen.main.plants.sub

import com.lab.greenpremium.data.Repository
import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.network.DefaultCallbackListener
import javax.inject.Inject

class PlantsSubPresenter @Inject constructor(val view: PlantsSubContract.View) : PlantsSubContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated(sectionPosition: Int) {
        updateSectionProducts(sectionPosition)
    }

    override fun updateSectionProducts(sectionPosition: Int) {
        UserModel.catalogSectionsData?.sections?.let { sections ->
            repository.getSectionProductsList(sections[sectionPosition].id, object : DefaultCallbackListener(view) {
                override fun onSuccess() {
                    sections[sectionPosition].products?.let { products ->
                        this@PlantsSubPresenter.view.initializeCatalog(products)
                    }
                }
            })
        }
    }

}