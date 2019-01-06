package com.lab.greenpremium.ui.screens.main.portfolio.sub

import javax.inject.Inject

class PortfolioSubPresenter @Inject constructor(val view: PortfolioSubContract.View) : PortfolioSubContract.Presenter {

    override fun onClickImage(pos: Int) {
        view.goToGalleryScreen(pos)
    }
}