package com.lab.greenpremium.data.entity

data class ObjectInfo(
        val biologists: List<Contact>,
        val coordinates: Coordinates,
        val id: String,
        val payment: String,
        val schedule: String) {

    val time: Long = System.currentTimeMillis()

}

data class Coordinates(
        val lat: String,
        val lot: String
)

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