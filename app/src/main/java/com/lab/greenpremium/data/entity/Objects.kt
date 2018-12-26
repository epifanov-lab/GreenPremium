package com.lab.greenpremium.data.entity

data class ObjectInfo(
        val biologists: List<Contact>,
        val id: String,
        val order_id: String,
        val payment: String,
        val schedule: String,
        val order_supply_date: String) {

    val time: Long = System.currentTimeMillis()

}

/**
 * get /objects/map
 * auth = false
 * */
data class MapObjectsData(
        val features: List<Feature>,
        val type: String
)

data class Feature(
        val geometry: Geometry,
        val id: Int,
        val properties: Properties,
        val type: String
)

data class Properties(
        val balloonContent: String,
        val iconContent: String
)

data class Geometry(
        val coordinates: List<Double>,
        val type: String
)