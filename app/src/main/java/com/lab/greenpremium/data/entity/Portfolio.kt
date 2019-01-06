package com.lab.greenpremium.data.entity

import java.io.Serializable

data class PortfolioResponse(val sections: List<PortfolioSection>?) {
    val time: Long = System.currentTimeMillis()
}

data class PortfolioSection(
        val id: String,
        val name: String,
        val photos: List<Photo>,
        val sort: String) : Serializable {

    fun getPhotosUrls(): ArrayList<String> {
        val result = arrayListOf<String>()
        photos.forEach { result.add(it.url) }
        return result
    }
}

data class Photo(
        val height: String,
        val id: String,
        val url: String,
        val width: String
) : Serializable

fun getPhotosUrls(photos: List<Photo>): ArrayList<String> {
    val result = arrayListOf<String>()
    photos.forEach { result.add(it.url) }
    return result
}