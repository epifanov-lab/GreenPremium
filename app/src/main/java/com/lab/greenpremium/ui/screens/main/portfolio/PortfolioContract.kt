package com.lab.greenpremium.ui.screens.main.portfolio

import com.lab.greenpremium.data.entity.PortfolioSection
import com.lab.greenpremium.ui.screens.base.BaseContract


interface PortfolioContract {
    interface View : BaseContract.BaseView {
        fun initializeTabs(portfolio: List<PortfolioSection>)
    }

    interface Presenter {
        fun onViewCreated()
    }
}