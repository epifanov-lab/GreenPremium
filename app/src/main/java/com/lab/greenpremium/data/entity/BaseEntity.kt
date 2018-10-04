package com.lab.greenpremium.data.entity


open class BaseResponse<T>(val status: String,
                           val title: String,
                           val data: T)

open class BodyRequest