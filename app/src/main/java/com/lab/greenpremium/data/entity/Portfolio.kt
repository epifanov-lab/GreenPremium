package com.lab.greenpremium.data.entity

import java.io.Serializable


data class Portfolio(
        val id: String,
        val name: String,
        val photos: List<Photo>,
        val sort: String) {

    val time: Long = System.currentTimeMillis()

}

data class Photo(
        val height: String,
        val id: String,
        val url: String,
        val width: String
) : Serializable