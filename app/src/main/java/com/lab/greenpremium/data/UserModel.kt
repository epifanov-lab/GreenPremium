package com.lab.greenpremium.data

import com.lab.greenpremium.data.entity.*


object UserModel {

    var authResponse: AuthResponse? = null

    var contactsResponse: ContactsResponse? = null

    var objectInfoResponse: ObjectInfoResponse? = null

    var eventsResponse: EventsResponse? = null

    var orderResponse: OrderResponse? = null

    var catalog: CatalogSectionsResponse? = null

    var meetingsListResponse: MeetingsListResponse? = null

    var portfolio: PortfolioResponse? = null

    var mapObjectsResponse: MapObjectsResponse? = null

    fun getFavoritesProductsList(): List<Product> {
        val favorites = arrayListOf<Product>()
        catalog?.sections?.forEach { section ->
            section.products?.forEach { product ->
                if (product.isFavorite) favorites.add(product)
            }
        }
        return favorites
    }

    fun getProductsInCartList(): List<Product> {
        val cart = arrayListOf<Product>()
        catalog?.sections?.forEach { section ->
            section.products?.forEach { product ->
                if (product.quantity > 0) cart.add(product)
            }
        }
        return cart
    }

    fun getCountOfItemsInCart(): Int {
        return 0 // todo plants.filter { it.quantity > 0 }.size
    }

}