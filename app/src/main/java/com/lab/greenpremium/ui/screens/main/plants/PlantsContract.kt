package com.lab.greenpremium.ui.screens.main.plants

import com.lab.greenpremium.data.entity.Section
import com.lab.greenpremium.ui.screens.base.BaseContract


interface PlantsContract {
    interface View : BaseContract.BaseView {
        fun initializeTabs(sections: List<Section>?)
    }

    interface Presenter {
        fun onViewCreated()
    }
}