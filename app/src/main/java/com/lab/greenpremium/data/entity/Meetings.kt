package com.lab.greenpremium.data.entity


data class MeetingAddRequest(
        val manager_id: String,
        val date: String
)

data class MeetingsAddResponse(
        val meeting_id: Int,
        val event_id: Int
)

data class MeetingsListRequest(
        val page: Int? = null
)

data class MeetingsListResponse(
        val meetings: List<Meeting> ) {

    val time: Long = System.currentTimeMillis()

}

data class Meeting(
        val created: String,
        val date: String,
        val manager: Contact,
        val status: Status
)

data class Status(
        val code: String,
        val title: String
)

enum class MeetingStatusCode (val code: String){
    NEW("22"),
    APPROVED("23"),
    REJECTED("24")
}
