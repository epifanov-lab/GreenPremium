package com.lab.greenpremium.data.entity

import com.google.gson.annotations.SerializedName

data class ObjectInfoResponse(
        @SerializedName("carousel") var carousel: List<Contact>,
        @SerializedName("id") val id: String,
        @SerializedName("order_id") val order_id: Int?,
        @SerializedName("payment") val payment: Double,
        @SerializedName("schedule") val schedule: String,
        @SerializedName("order_supply_date") val order_supply_date: String?) {

    val time: Long = System.currentTimeMillis()
}

/**
 * get /objects/map
 * auth = false
 * */
data class MapObjectsResponse(
        @SerializedName("features") val features: List<Feature>,
        @SerializedName("type") val type: String
)

data class Feature(
        @SerializedName("geometry") val geometry: Geometry,
        @SerializedName("id") val id: Int,
        @SerializedName("properties") val properties: Properties,
        @SerializedName("type") val type: String
)

data class Properties(
        @SerializedName("balloonContent") val balloonContent: String,
        @SerializedName("iconContent") val iconContent: String
)

data class Geometry(
        @SerializedName("coordinates") val coordinates: List<Double>,
        @SerializedName("type") val type: String
)