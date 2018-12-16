package com.lab.greenpremium.ui.screen.main.plants

import com.lab.greenpremium.data.Repository
import com.lab.greenpremium.data.network.DefaultCallbackListener
import javax.inject.Inject

class PlantsPresenter @Inject constructor(val view: PlantsContract.View) : PlantsContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        repository.getCatalogSections(object : DefaultCallbackListener(view){
            override fun onSuccess() {

            }
        })
    }

}