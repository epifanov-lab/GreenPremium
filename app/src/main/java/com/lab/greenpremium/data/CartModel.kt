package com.lab.greenpremium.data

import com.lab.greenpremium.data.entity.CartResponse
import com.lab.greenpremium.data.entity.CatalogSectionsResponse
import com.lab.greenpremium.data.entity.Product


object CartModel {

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

                val offerFromCart = cartProduct.getChosenOffer()
                val offerFromCatalog = catalogProduct.getChosenOffer()

                if (offerFromCatalog.product_id == offerFromCart.product_id) {
                    catalogProduct.quantity = cartProduct.quantity
                    return@iterator
                }

            }
        }
    }

    fun syncFavoritesWithCart() {
        cart?.products?.forEach{
            syncFavoritesWithCartByProduct(it)
        }
    }

    fun syncCatalogWithFavorites() {
        favorites?.forEach {
            syncCatalogWithFavoritesByProduct(it)
        }
    }

    fun syncFavoritesWithCartByProduct(other: Product) {
        favorites?.forEach {favoriteProduct ->

            val offerFromCart = other.getChosenOffer()
            val offerFromFavorites = favoriteProduct.getChosenOffer()

            if (offerFromFavorites.product_id == offerFromCart.product_id) {
                favoriteProduct.quantity = other.quantity
            }
        }
    }

    //TODO REFACTOR THIS! need to be one common pool of products!!!

    fun syncCatalogWithFavoritesByProduct(other: Product) {
        catalog?.sections?.forEach {sections ->
            sections.products?.forEach { catalogProduct ->
                val offerFromCatalog = catalogProduct.getChosenOffer()
                val offerFromFavorites = other.getChosenOffer()

                if (offerFromCatalog.product_id == offerFromFavorites.product_id) {
                    catalogProduct.isFavorite = true
                }
            }
        }
    }


    fun getCartTotalCost(): Double {
        var result = 0.0
        cart?.products?.forEach { result += it.offers[0].price * it.quantity }
        return result
    }

    fun clearModel() {
        cart = null
        favorites = null
        catalog = null
    }
}

