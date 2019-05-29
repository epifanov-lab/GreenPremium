package com.lab.greenpremium.data

import com.lab.greenpremium.data.entity.CartResponse
import com.lab.greenpremium.data.entity.CatalogSectionsResponse
import com.lab.greenpremium.data.entity.FavoritesResponse
import com.lab.greenpremium.data.entity.Product


object CartModel {

    /* TODO Общий пул айтемов
    *  Sync методы убрать
    *  Хранить все продукты в одной мапе
    *  ключ = product_id
    *  value = product
    *  if (product_id != offer_id) new Product(offer_id)
    *  подумать
    * */

    var cart: CartResponse? = null
    var favorites: FavoritesResponse? = null
    var catalog: CatalogSectionsResponse? = null

    fun syncCatalogWithCart() {
        cart?.products?.forEach {
            syncCatalogWithCartByProduct(it)
        }
    }

    fun syncCatalogWithCartByProduct(cartProduct: Product) {
        catalog?.sections?.forEach iterator@{ sections ->
            sections.products?.forEach { catalogProduct ->

                val offerFromCatalog = catalogProduct.getSelectedOffer()
                val offerFromCart = cartProduct.getSelectedOffer()

                if (offerFromCatalog.product_id == offerFromCart.product_id) {
                    offerFromCatalog.quantity = offerFromCart.quantity
                    return@iterator
                }

            }
        }
    }

    fun syncFavoritesWithCart() {
        cart?.products?.forEach { cartProduct ->
            favorites?.products?.forEach { favoriteProduct ->

                val offerFromCart = cartProduct.getSelectedOffer()
                val offerFromFavorites = favoriteProduct.getSelectedOffer()

                if (offerFromFavorites.product_id == offerFromCart.product_id) {
                    offerFromFavorites.quantity = offerFromCart.quantity
                }
            }
        }
    }

    fun syncCatalogWithFavorites() {
        favorites?.products?.apply {
            if (size > 0) {
                forEach { favoriteProduct ->
                    catalog?.sections?.forEach { sections ->
                        sections.products?.forEach { catalogProduct ->

                            val offerFromFavorites = favoriteProduct.getSelectedOffer()
                            val offerFromCatalog = catalogProduct.getSelectedOffer()
                            catalogProduct.isFavorite = offerFromCatalog.product_id == offerFromFavorites.product_id
                        }
                    }
                }
            } else {
                catalog?.sections?.forEach { sections ->
                    sections.products?.forEach { catalogProduct ->
                        catalogProduct.isFavorite = false
                    }
                }
            }
        }
    }

    fun getCartTotalCost(): Double {
        var result = 0.0
        cart?.products?.forEach { result += it.getSelectedOffer().price * it.getSelectedOffer().quantity }
        return result
    }

    fun clear() {
        cart = null
        favorites = null
        catalog = null
    }

    fun clearCatalog() {
        catalog = null
    }

    fun clearCart() {
        cart?.let { CartModel.cart!!.products.clear() }
        syncCatalogWithCart()
    }
}

