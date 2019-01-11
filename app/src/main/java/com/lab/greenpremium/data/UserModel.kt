package com.lab.greenpremium.data

import com.lab.greenpremium.data.entity.*


object UserModel {

    var authResponse: AuthResponse? = null

    var contactsResponse: ContactsResponse? = null

    var objectInfoResponse: ObjectInfoResponse? = null

    var eventsResponse: EventsResponse? = null

    var orderResponse: OrderResponse? = null

    var catalogSectionsResponse: CatalogSectionsResponse? = null

    var cart: List<Product>? = null

    var meetingsListResponse: MeetingsListResponse? = null

    var portfolio: PortfolioResponse? = null

    var mapObjectsResponse: MapObjectsResponse? = null


    fun getFavoritesProductsList(): List<Product> {
        val favorites = arrayListOf<Product>()
        catalogSectionsResponse?.sections?.forEach { section ->
            section.products?.forEach { product ->
                if (product.isFavorite) favorites.add(product)
            }
        }
        return favorites
    }

    fun getProductsInCartList(): List<Product> {
        val favorites = arrayListOf<Product>()
        catalogSectionsResponse?.sections?.forEach { section ->
            section.products?.forEach { product ->
                if (product.count > 0) favorites.add(product)
            }
        }
        return favorites
    }

    fun getCountOfItemsInCart(): Int {
        return 0 // todo plants.filter { it.count > 0 }.size
    }

}