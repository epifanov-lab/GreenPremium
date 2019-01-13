package com.lab.greenpremium.data

import com.lab.greenpremium.data.entity.CatalogSectionsResponse
import com.lab.greenpremium.data.entity.Product


object CartModel {

    lateinit var cart: MutableList<Product>
    var catalog: CatalogSectionsResponse? = null

    fun syncCatalogWithCart() {
        cart.forEach {
            syncCatalogWithCartByProduct(it)
        }
    }

    fun syncCatalogWithCartByProduct(cartProduct: Product) {
        catalog?.sections?.forEach iterator@ { sections ->
            sections.products?.forEach { catalogProduct ->

                val offerFromCart = cartProduct.offers[cartProduct.selectedOfferPosition]
                val offerFromCatalog = catalogProduct.offers[catalogProduct.selectedOfferPosition]

                if (offerFromCatalog.product_id == offerFromCart.product_id) {
                    catalogProduct.quantity = cartProduct.quantity
                    return@iterator
                }

            }
        }
    }

    fun getCartTotalCost(): Double {
        var result = 0.0
        cart.forEach { result += it.offers[0].price * it.quantity }
        return result
    }

    fun getFavoritesProductsList(): List<Product> {
        val favorites = arrayListOf<Product>()
        catalog?.sections?.forEach { section ->
            section.products?.forEach { product ->
                if (product.isFavorite) favorites.add(product)
            }
        }
        return favorites
    }
}

