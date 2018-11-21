package com.lab.greenpremium.data.entity

data class AuthRequest(val login: String,
                       val password: String)

data class AuthData(val id: String,
                    val name: String,
                    val email: String,
                    val phone: String,
                    val position: String,
                    val photo: String,
                    val token: String)