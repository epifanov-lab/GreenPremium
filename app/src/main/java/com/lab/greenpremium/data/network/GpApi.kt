package com.lab.greenpremium.data.network

import com.lab.greenpremium.data.entity.AuthData
import com.lab.greenpremium.data.entity.AuthRequest
import com.lab.greenpremium.data.entity.BaseResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST


interface GpApi {

    @POST("auth")
    fun auth(@Body request: AuthRequest): Single<BaseResponse<AuthData>>

}