package com.lab.greenpremium.data.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class GpInterceptor : Interceptor{

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
                //.addHeader("Accept", "application/json")
                //.addHeader("Content-Type", "application/json;charset=UTF-8")
                .build()
        return chain.proceed(request)
    }
}