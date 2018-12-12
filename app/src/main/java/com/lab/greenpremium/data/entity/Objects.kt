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