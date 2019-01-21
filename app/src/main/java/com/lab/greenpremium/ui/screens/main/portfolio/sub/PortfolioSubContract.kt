package com.lab.greenpremium.ui.screens.main.portfolio.sub

import com.lab.greenpremium.data.entity.Photo
import com.lab.greenpremium.ui.screens.base.BaseContract


interface PortfolioSubContract {
    interface View : BaseContract.BaseView {
        fun initializeImagesGrid(photos: List<Photo>)
        fun goToGalleryScreen(photos: List<Photo>, pos: Int)
    }

    interface Presenter {
        fun onViewCreated(photos: List<Photo>)
        fun onClickImage(position: Int)
    }
}