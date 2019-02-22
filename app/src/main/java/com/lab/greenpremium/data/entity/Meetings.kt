package com.lab.greenpremium.data.entity

import com.google.gson.annotations.SerializedName


data class MeetingAddRequest(
        @SerializedName("manager_id") val manager_id: String,
        @SerializedName("date") val date: String
)

data class MeetingsAddResponse(
        @SerializedName("meeting_id") val meeting_id: Int,
        @SerializedName("event_id") val event_id: Int
)

data class MeetingsListRequest(
        @SerializedName("page") val page: Int? = null
)

data class MeetingsListResponse(
        @SerializedName("meetings") val meetings: List<Meeting>) {

    val time: Long = System.currentTimeMillis()

}

data class Meeting(
        @SerializedName("created") val created: String,
        @SerializedName("date") val date: String,
        @SerializedName("manager") val manager: Contact,
        @SerializedName("status") val status: Status
)

data class Status(
        @SerializedName("code") val code: String,
        @SerializedName("title") val title: String
)

enum class MeetingStatusCode(@SerializedName("code") val code: String) {
    NEW("22"),
    APPROVED("23"),
    REJECTED("24")
}
