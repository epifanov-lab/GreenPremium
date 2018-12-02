package com.lab.greenpremium.data

import android.annotation.SuppressLint
import com.google.gson.JsonParser
import com.lab.greenpremium.REQUEST_REFRESH_TIME_MS
import com.lab.greenpremium.data.entity.AuthData
import com.lab.greenpremium.data.entity.AuthRequest
import com.lab.greenpremium.data.entity.BaseResponse
import com.lab.greenpremium.data.entity.ContactsData
import com.lab.greenpremium.data.local.PreferencesManager
import com.lab.greenpremium.data.network.ApiError
import com.lab.greenpremium.data.network.ApiMethods
import com.lab.greenpremium.data.network.CallbackListener
import com.lab.greenpremium.utills.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject


class Repository @Inject constructor(private val apiMethods: ApiMethods,
                                     private val preferences: PreferencesManager) {

    @SuppressLint("CheckResult")
    fun auth(login: String, password: String, listener: CallbackListener) {
        apiMethods.auth(AuthRequest(login, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response -> handleResponse(response, listener) },
                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun updateContacts(listener: CallbackListener) {

        if (UserModel.contacts != null) {
            if (System.currentTimeMillis() - UserModel.contacts!!.time < REQUEST_REFRESH_TIME_MS) {
                listener.onSuccess()
            }
        }

        apiMethods.getContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response -> handleResponse(response, listener) },
                        { error -> handleError(error, listener) }
                )
    }

    private inline fun <reified DATA> handleResponse(response: BaseResponse<DATA>, listener: CallbackListener) {
        LogUtil.i("HANDLE_RESPONSE: ${response.data}")

        if (response.status == 200) {
            when (DATA::class) {

                AuthData::class -> {
                    val authData = response.data as AuthData
                    UserModel.authData = authData
                    preferences.setToken(authData.token)
                }

                ContactsData::class -> {
                    UserModel.contacts = response.data as ContactsData
                }
            }

            listener.onSuccess()

        } else {
            listener.onError(ApiError(response.status, response.title))
        }

    }

    private fun handleError(throwable: Throwable, listener: CallbackListener) {
        LogUtil.i("HANDLE_ERROR: $throwable")

        fun getResponseErrorFromJson(json: String): ApiError {
            return ApiError(
                    JsonParser().parse(json).asJsonObject["status"].asInt,
                    JsonParser().parse(json).asJsonObject["title"].asString
            )
        }

        if (throwable is HttpException) {
            val body = throwable.response().errorBody()?.string()

            if (body != null) listener.onError(getResponseErrorFromJson(body))
            else listener.onError(throwable)

        } else {
            listener.onError(throwable)
        }
    }
}