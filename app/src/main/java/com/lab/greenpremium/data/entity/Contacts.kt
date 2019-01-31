package com.lab.greenpremium.data.entity

import com.google.gson.annotations.SerializedName

data class ContactsResponse(@SerializedName("office") val office: Contact,
                            @SerializedName("managers") val managers: MutableList<Contact>?) {

    val time: Long = System.currentTimeMillis()

    fun getManagers(useOffice: Boolean = false): MutableList<Contact> {
        val result: MutableList<Contact> = mutableListOf<Contact>()
        if (managers != null) result.addAll(managers)
        if (useOffice) result.add(office)
        return result
    }

    fun getManagersAvailableForMeeting(): MutableList<Contact> {
        val result: MutableList<Contact> = ArrayList()
        managers?.forEach { if (it.is_meeting_available) result.add(it) }
        return result
    }
}

data class Contact(@SerializedName("id") val id: String,
                   @SerializedName("name") val name: String,
                   @SerializedName("email") val email: String,
                   @SerializedName("phone") val phone: String,
                   @SerializedName("position") val position: String,
                   @SerializedName("photo") val photo: String,
                   @SerializedName("is_meeting_available") val is_meeting_available: Boolean,
                   var schedule: String)