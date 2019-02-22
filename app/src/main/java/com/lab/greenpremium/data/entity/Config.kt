package com.lab.greenpremium.data.entity

import com.google.gson.annotations.SerializedName


//get /config
data class GetConfigResponse(
        @SerializedName("show_demo_button") val show_demo_button: Boolean,
        @SerializedName("show_register_button") val show_register_button: Boolean,
        @SerializedName("show_forgot_password_button") val show_forgot_password_button: Boolean
)