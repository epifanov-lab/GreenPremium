package com.lab.greenpremium.data

import com.lab.greenpremium.data.entity.CartResponse
import com.lab.greenpremium.data.entity.CatalogSectionsResponse
import com.lab.greenpremium.data.entity.Product


object CartModel {

    lateinit var cart: CartResponse
    lateinit var favorites: MutableList<Product>
    var catalog: CatalogSectionsResponse? = null

    fun syncCatalogWithCart() {
        cart.products.forEach {
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
        cart.products.forEach{
            syncFavoritesWithCartByProduct(it)
        }
    }

    fun syncFavoritesWithCartByProduct(cartProduct: Product) {
        favorites.forEach {favoriteProduct ->

            val offerFromCart = cartProduct.getChosenOffer()
            val offerFromCatalog = favoriteProduct.getChosenOffer()

            if (offerFromCatalog.product_id == offerFromCart.product_id) {
                favoriteProduct.quantity = cartProduct.quantity
                return
            }
        }
    }

    fun getCartTotalCost(): Double {
        var result = 0.0
        cart.products.forEach { result += it.offers[0].price * it.quantity }
        return result
    }

}

