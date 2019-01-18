package com.lab.greenpremium.data.entity

data class EventsResponse(val events: MutableList<Event>) {
    val time: Long = System.currentTimeMillis()
    var page: Int = 1 // first page as default
}


data class Event(
        val client_id: String,
        val created: String,
        val email_to: String,
        val id: String,
        val manager_id: String,
        val message: String,
        val object_id: String,
        val subject: String,
        val file_path: String
)