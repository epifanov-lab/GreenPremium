package com.lab.greenpremium.ui.screens.main.portfolio.sub

import com.lab.greenpremium.ui.screens.base.BaseContract


interface PortfolioSubContract {
    interface View : BaseContract.BaseView {
        fun goToGalleryScreen(pos: Int)
    }

    interface Presenter {
        fun onClickImage(position: Int)
    }
}