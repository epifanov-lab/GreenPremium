package com.lab.greenpremium.data.entity

import com.google.gson.annotations.SerializedName

data class AuthRequest(@SerializedName("login") val login: String,
                       @SerializedName("password") val password: String)

data class AuthData(@SerializedName("id") val id: String,
                    @SerializedName("name") val name: String,
                    @SerializedName("email") val email: String,
                    @SerializedName("phone") val phone: String,
                    @SerializedName("position") val position: String,
                    @SerializedName("photo") val photo: String,
                    @SerializedName("token") val token: String) {

    val time: Long = System.currentTimeMillis()
}