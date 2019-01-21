package com.lab.greenpremium.ui.screens.main.portfolio.sub

import com.lab.greenpremium.data.entity.Photo
import javax.inject.Inject

class PortfolioSubPresenter @Inject constructor(val view: PortfolioSubContract.View) : PortfolioSubContract.Presenter {

    lateinit var photos: List<Photo>

    override fun onViewCreated(photos: List<Photo>) {
        this.photos = photos
        view.initializeImagesGrid(photos)
    }

    override fun onClickImage(pos: Int) {
        view.goToGalleryScreen(photos, pos)
    }

}