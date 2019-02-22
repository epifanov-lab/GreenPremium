package com.lab.greenpremium.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PortfolioResponse(@SerializedName("sections") val sections: List<PortfolioSection>?) {
    val time: Long = System.currentTimeMillis()
}

data class PortfolioSection(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("photos") val photos: List<Photo>,
        @SerializedName("sort") val sort: String) : Serializable {

    fun getPhotosUrls(): ArrayList<String> {
        val result = arrayListOf<String>()
        photos.forEach { result.add(it.url) }
        return result
    }
}

data class Photo(
        @SerializedName("height") val height: String,
        @SerializedName("id") val id: String,
        @SerializedName("url") val url: String,
        @SerializedName("width") val width: String
) : Serializable

//todo move to utils
fun getPhotosUrls(photos: List<Photo>): ArrayList<String> {
    val result = arrayListOf<String>()
    photos.forEach { result.add(it.url) }
    return result
}