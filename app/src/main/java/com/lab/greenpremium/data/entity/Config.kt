package com.lab.greenpremium.data.entity


//get /config
data class GetConfigResponse(
        val show_demo_button: Boolean,
        val show_register_button: Boolean,
        val show_forgot_password_button: Boolean
)