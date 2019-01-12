package com.lab.greenpremium.ui.screens.main.plants

import com.lab.greenpremium.data.repo.Repository
import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.network.DefaultCallbackListener
import javax.inject.Inject

class PlantsPresenter @Inject constructor(val view: PlantsContract.View) : PlantsContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        repository.getCatalogSections(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                val catalogSectionsData = UserModel.catalog
                catalogSectionsData?.let {
                    this@PlantsPresenter.view.initializeTabs(catalogSectionsData.sections)
                }
            }
        })
    }

}