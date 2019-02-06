package com.lab.greenpremium.ui.screens.main.portfolio

import com.lab.greenpremium.data.repository.Repository
import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.network.DefaultCallbackListener
import javax.inject.Inject

class PortfolioPresenter @Inject constructor(val view: PortfolioContract.View) : PortfolioContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        repository.updatePortfolio(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                val portfolio = UserModel.portfolio
                portfolio?.sections?.let {
                    this@PortfolioPresenter.view.initializeTabs(portfolio.sections)
                }
            }
        })
    }
}