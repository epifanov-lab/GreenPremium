package com.lab.greenpremium.data.entity

data class ObjectInfoResponse(
        var biologists: List<Contact>,
        val id: String,
        val order_id: Int?,
        val payment: Double,
        val schedule: String,
        val order_supply_date: String?) {

    val time: Long = System.currentTimeMillis()

    //менеджеры обладают свойством is_meeting_available == true и должны идти первыми в списке
    //schedule должен быть только у биологов
    fun updateBiologists() {
        biologists = biologists.sortedWith(compareBy { !it.is_meeting_available })
        biologists.forEach {
            if (!it.is_meeting_available) it.schedule = schedule
        }
    }
}

/**
 * get /objects/map
 * auth = false
 * */
data class MapObjectsResponse(
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