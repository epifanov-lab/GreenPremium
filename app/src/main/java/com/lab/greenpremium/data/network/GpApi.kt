package com.lab.greenpremium.data.network

import com.lab.greenpremium.data.entity.*
import io.reactivex.Single
import retrofit2.http.*
import javax.inject.Inject


class ApiMethods @Inject constructor(private val api: GpApi) {

    fun auth(request: AuthRequest): Single<BaseResponse<AuthData>> {
        return api.auth(request.login, request.password)
    }

    fun getContacts(): Single<BaseResponse<ContactsData>> {
        return api.getContacts()
    }

    fun getObjectInfo(token: String): Single<BaseResponse<ObjectInfo>> {
        return api.getObjectInfo(token)
    }

    fun getEvents(token: String): Single<BaseResponse<List<Event>>> {
        return api.getEvents(token)
    }

    fun getMeetingsList(token: String): Single<BaseResponse<List<Meeting>>> {
        return api.getMeetingsList(token)
    }

    fun addMeeting(token: String, manager_id: String, date: String): Single<BaseResponse<MeetingsAddResponse>> {
        return api.addMeeting(token, manager_id, date)
    }

    fun getPortfolio(): Single<BaseResponse<Portfolio>> {
        return api.getPortfolio()
    }

}

interface GpApi {

    @POST("auth")
    @FormUrlEncoded
    fun auth(@Field("login") login: String,
             @Field("password") password: String): Single<BaseResponse<AuthData>>

    @GET("contacts")
    fun getContacts(): Single<BaseResponse<ContactsData>>

    @GET("objects/info")
    fun getObjectInfo(@Header("X-Auth-Token") token: String): Single<BaseResponse<ObjectInfo>>

    @GET("events")
    fun getEvents(@Header("X-Auth-Token") token: String): Single<BaseResponse<List<Event>>>

    @GET("meetings")
    fun getMeetingsList(@Header("X-Auth-Token") token: String): Single<BaseResponse<List<Meeting>>>

    @POST("meetings/add")
    @FormUrlEncoded
    fun addMeeting(@Header("X-Auth-Token") token: String,
                   @Field("manager_id") manager_id: String,
                   @Field("date") date: String): Single<BaseResponse<MeetingsAddResponse>>

    @GET("portfolio")
    fun getPortfolio(): Single<BaseResponse<Portfolio>>

}