package com.lab.greenpremium.data.entity

import com.google.gson.annotations.SerializedName

data class ContactsData(@SerializedName("office") val office: OfficeContact,
                        @SerializedName("managers") val managers: List<ManagerContact>) {
    //TODO PROBLEM IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT

    val time: Long = System.currentTimeMillis()
}

data class OfficeContact(@SerializedName("name") val name: String,
                         @SerializedName("email") val email: String,
                         @SerializedName("phone") val phone: String)

data class ManagerContact(@SerializedName("id") val id: String,
                          @SerializedName("name") val name: String,
                          @SerializedName("email") val email: String,
                          @SerializedName("phone") val phone: String,
                          @SerializedName("position") val position: String,
                          @SerializedName("photo") val photo: String)