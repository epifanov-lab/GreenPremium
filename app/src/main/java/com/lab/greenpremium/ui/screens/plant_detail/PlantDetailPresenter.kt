package com.lab.greenpremium.ui.screens.plant_detail

import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repo.Repository
import com.lab.greenpremium.utills.currencyFormat
import javax.inject.Inject

class PlantDetailPresenter @Inject constructor(val view: PlantDetailContract.View) : PlantDetailContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    lateinit var product: Product

    override fun onViewCreated(product: Product) {
        this.product = product
        val offer = product.getChosenOffer()

        view.setTitle(product.name)
        view.setPhoto(product.photo)
        view.updateFavoriteButtonState(product.isFavorite)
        view.initializeCounter(product)
        view.setPrice(currencyFormat(offer.price), currencyFormat(offer.old_price))
        view.setShortInfo(offer.getParams())
        view.setLongInfo(product.detail_text)
        view.initializeGallery(product.gallery)
    }

    override fun onClickFavorite() {
        product.changeFavoriteState()
        repository.editFavorites(product, object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                this@PlantDetailPresenter.view.updateFavoriteButtonState(product.isFavorite)
            }
        })
    }

}