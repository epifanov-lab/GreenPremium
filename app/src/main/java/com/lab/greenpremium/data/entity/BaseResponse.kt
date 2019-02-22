package com.lab.greenpremium.data.entity

import com.google.gson.annotations.SerializedName


open class BaseResponse<DATA>(@SerializedName("status") val status: Int,
                              @SerializedName("title") val title: String,
                              @SerializedName("data") val data: DATA)