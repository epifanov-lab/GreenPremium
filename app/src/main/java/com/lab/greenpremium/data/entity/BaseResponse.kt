package com.lab.greenpremium.data.entity


open class BaseResponse<DATA>(val status: Int,
                              val title: String,
                              val data: DATA)