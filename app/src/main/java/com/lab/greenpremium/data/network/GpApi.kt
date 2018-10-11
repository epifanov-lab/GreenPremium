package com.lab.greenpremium.data.network

import com.lab.greenpremium.data.entity.AuthData
import com.lab.greenpremium.data.entity.AuthRequest
import com.lab.greenpremium.data.entity.BaseResponse
import com.lab.greenpremium.utills.LogUtil
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Inject


class ApiMethods @Inject constructor(private val api: GpApi) {

    fun auth(request: AuthRequest): Single<BaseResponse<AuthData>> {
        LogUtil.i("HTTP_LOG REQUEST: $request")
        return api.auth(request.login, request.password)
    }

}

interface GpApi {

    @FormUrlEncoded
    @POST("auth")
    fun auth(@Field("login") login: String,
             @Field("password") password: String): Single<BaseResponse<AuthData>>

}