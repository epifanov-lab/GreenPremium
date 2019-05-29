package com.lab.greenpremium.data.entity

import com.google.gson.annotations.SerializedName

//post /catalog/favorites/add
//post /catalog/favorites/delete
//get /catalog/favorites

data class EditFavoritesRequest(@SerializedName("is_favorite") val is_favorite: Boolean,
                                @SerializedName("product_id") val product_id: Int)

data class EditFavoriteResponse(@SerializedName("is_deleted") val is_deleted: Boolean?,
                                @SerializedName("favorite_id") val favorite_id: Int?)

data class FavoritesResponse(@SerializedName("products") val products: MutableList<Product>)

