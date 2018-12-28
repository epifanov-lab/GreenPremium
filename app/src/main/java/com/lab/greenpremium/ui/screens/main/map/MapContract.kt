package com.lab.greenpremium.ui.screens.main.map

import com.lab.greenpremium.data.entity.Feature
import com.lab.greenpremium.ui.screens.base.BaseContract


interface MapContract {
    interface View : BaseContract.BaseView {
        fun placeMarkers(objects: List<Feature>)
    }

    interface Presenter {
        fun updateMapObjects()
    }
}