package com.lab.greenpremium.data.entity


//post /catalog/favorites/add
//post /catalog/favorites/delete
//get /catalog/favorites

data class EditFavoritesRequest(val is_favorite: Boolean, val product_id: Int)
data class EditFavoriteResponse(val is_deleted: Boolean?, val favorite_id: Int?)

data class GetFavoritesResponse(val products: MutableList<Product>)

