package com.lab.greenpremium.ui.screens.plant_detail

import com.lab.greenpremium.data.CartModel
import com.lab.greenpremium.data.CartUpdatedEvent
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repository.Repository
import com.lab.greenpremium.utills.currencyFormat
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class PlantDetailPresenter @Inject constructor(val view: PlantDetailContract.View) : PlantDetailContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    lateinit var product: Product

    override fun onViewCreated(product: Product) {
        this.product = product
        val offer = product.getSelectedOffer()

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

            override fun onError(throwable: Throwable) {
                super.onError(throwable)
                product.changeFavoriteState()
            }
        })
    }

    override fun onProductQuantityChanged(product: Product) {
        repository.addToCart(product.getSelectedOffer().product_id, product.getSelectedOffer().quantity,
                object : DefaultCallbackListener(view) {
                    override fun onSuccess() {
                        CartModel.syncCatalogWithCartByProduct(product)
                        EventBus.getDefault().post(CartUpdatedEvent())
                    }
                })
    }

    override fun onClickImage(pos: Int) {
        view.goToGalleryScreen(product.gallery, pos)
    }

}