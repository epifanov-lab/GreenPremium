package com.lab.greenpremium.ui.screen.main.plants

import com.lab.greenpremium.data.entity.Section
import com.lab.greenpremium.ui.screen.base.BaseContract


interface PlantsContract {
    interface View : BaseContract.BaseView {
        fun initializeTabs(sections: List<Section>?)
    }

    interface Presenter {
        fun onViewCreated()
    }
}