package com.lab.greenpremium.ui.screen.main.portfolio

import com.lab.greenpremium.data.entity.PortfolioSection
import com.lab.greenpremium.ui.screen.base.BaseContract


interface PortfolioContract {
    interface View : BaseContract.BaseView {
        fun initializeTabs(portfolio: List<PortfolioSection>)
    }

    interface Presenter {
        fun onViewCreated()
    }
}