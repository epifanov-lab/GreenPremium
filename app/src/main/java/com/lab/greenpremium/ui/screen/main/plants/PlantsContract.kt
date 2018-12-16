package com.lab.greenpremium.ui.screen.main.plants

import com.lab.greenpremium.ui.screen.base.BaseContract


interface PlantsContract {
    interface View : BaseContract.BaseView {
        fun initializeTabs()
    }

    interface Presenter {
        fun onViewCreated()
    }
}