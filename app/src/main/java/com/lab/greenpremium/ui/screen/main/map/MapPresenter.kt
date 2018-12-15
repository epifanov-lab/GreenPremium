package com.lab.greenpremium.ui.screen.main.map

import com.lab.greenpremium.data.Repository
import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.network.DefaultCallbackListener
import javax.inject.Inject

class MapPresenter @Inject constructor(val view: MapContract.View) : MapContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun updateMapObjects() {
        repository.updateMapObjects(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                val mapObjectsData = UserModel.mapObjectsData
                mapObjectsData?.let {
                    this@MapPresenter.view.placeMarkers(mapObjectsData.features)
                }
            }
        })
    }
}