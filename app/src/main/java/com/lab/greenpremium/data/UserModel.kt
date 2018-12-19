package com.lab.greenpremium.data

import com.lab.greenpremium.data.entity.*


object UserModel {

    var authData: AuthData? = null
    var contacts: ContactsData? = null
    var objectInfo: ObjectInfo? = null
    var eventsData: EventsData? = null
    var catalogSectionsData: CatalogSectionsData? = null
    var meetingsListData: MeetingsListData? = null
    var portfolio: Portfolio? = null
    var mapObjectsData: MapObjectsData? = null

    fun getFavoritesProductsList(): List<Product> {
        val favorites = arrayListOf<Product>()
        catalogSectionsData?.sections?.forEach { section ->
            section.products?.forEach { product ->
                if (product.isFavorite) favorites.add(product)
            }
        }
        return favorites
    }

    fun getProductsInCartList(): List<Product> {
        val favorites = arrayListOf<Product>()
        catalogSectionsData?.sections?.forEach { section ->
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