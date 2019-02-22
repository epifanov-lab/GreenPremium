package com.lab.greenpremium.data.entity

import com.google.gson.annotations.SerializedName

data class EventsResponse(@SerializedName("events") val events: MutableList<Event>) {
    val time: Long = System.currentTimeMillis()
    @SerializedName("page") var page: Int = 1 // first page as default
}


data class Event(
        @SerializedName("client_id") val client_id: String,
        @SerializedName("created") val created: String,
        @SerializedName("email_to") val email_to: String,
        @SerializedName("id") val id: String,
        @SerializedName("manager_id") val manager_id: String,
        @SerializedName("message") val message: String,
        @SerializedName("object_id") val object_id: String,
        @SerializedName("subject") val subject: String,
        @SerializedName("file_path") val file_path: String
)