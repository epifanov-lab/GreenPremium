package com.lab.greenpremium.data

import com.lab.greenpremium.data.entity.CartResponse
import com.lab.greenpremium.data.entity.CatalogSectionsResponse
import com.lab.greenpremium.data.entity.Product


object CartModel {

    //TODO REFACTOR THIS! need to be one common pool of products!!!

    var cart: CartResponse? = null
    var favorites: MutableList<Product>? = null
    var catalog: CatalogSectionsResponse? = null

    fun syncCatalogWithCart() {
        cart?.products?.forEach {
            syncCatalogWithCartByProduct(it)
        }
    }

    fun syncCatalogWithCartByProduct(cartProduct: Product) {
        catalog?.sections?.forEach iterator@ { sections ->
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
        cart?.products?.forEach{cartProduct ->
            favorites?.forEach {favoriteProduct ->

                val offerFromCart = cartProduct.getSelectedOffer()
                val offerFromFavorites = favoriteProduct.getSelectedOffer()

                if (offerFromFavorites.product_id == offerFromCart.product_id) {
                    offerFromFavorites.quantity = offerFromCart.quantity
                }
            }
        }
    }

    fun syncCatalogWithFavorites() {
        favorites?.forEach {favoriteProduct ->
            catalog?.sections?.forEach {sections ->
                sections.products?.forEach { catalogProduct ->

                    val offerFromCatalog = catalogProduct.getSelectedOffer()
                    val offerFromFavorites = favoriteProduct.getSelectedOffer()

                    if (offerFromCatalog.product_id == offerFromFavorites.product_id) {
                        catalogProduct.isFavorite = true
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

