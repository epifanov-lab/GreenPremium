package com.lab.greenpremium.ui.screens.main.plants

import com.lab.greenpremium.data.CartModel
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repository.Repository
import javax.inject.Inject

class PlantsPresenter @Inject constructor(val view: PlantsContract.View) : PlantsContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        repository.getCatalogSections(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                val catalogSectionsData = CartModel.catalog
                catalogSectionsData?.let {
                    this@PlantsPresenter.view.initializeTabs(catalogSectionsData.sections)
                }
            }
        })
    }

}