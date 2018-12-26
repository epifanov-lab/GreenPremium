package com.lab.greenpremium.data.entity

data class EventsResponse(val events: List<Event>) {
    val time: Long = System.currentTimeMillis()
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