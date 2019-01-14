package com.lab.greenpremium.ui.screens.plant_detail

import com.lab.greenpremium.data.entity.OfferParam
import com.lab.greenpremium.data.entity.Photo
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.screens.base.BaseContract


interface PlantDetailContract {
    interface View : BaseContract.BaseView {
        fun setTitle(title: String)
        fun setPhoto(photo: Photo)
        fun updateFavoriteButtonState(isFavorite: Boolean)
        fun initializeCounter(product: Product)
        fun setPrice(price: String?, oldPrice: String?)
        fun setShortInfo(params: Array<OfferParam?>)
        fun setLongInfo(info: String)
        fun initializeGallery(photos: List<Photo>)
        fun goToGalleryScreen(gallery: List<Photo>, pos: Int)
    }

    interface Presenter {
        fun onViewCreated(product: Product)
        fun onClickFavorite()
        fun onProductQuantityChanged(product: Product)
        fun onClickImage(pos: Int)
    }
}