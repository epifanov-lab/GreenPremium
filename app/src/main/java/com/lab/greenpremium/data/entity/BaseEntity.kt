package com.lab.greenpremium.data.entity


open class BodyRequest

open class BaseResponse<T>(val status: Int,
                           val title: String,
                           val data: T)
