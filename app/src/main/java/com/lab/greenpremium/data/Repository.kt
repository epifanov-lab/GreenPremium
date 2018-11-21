package com.lab.greenpremium.data

import android.annotation.SuppressLint
import com.lab.greenpremium.data.entity.AuthData
import com.lab.greenpremium.data.entity.AuthRequest
import com.lab.greenpremium.data.entity.BaseResponse
import com.lab.greenpremium.data.entity.Contacts
import com.lab.greenpremium.data.local.PreferencesManager
import com.lab.greenpremium.data.network.ApiMethods
import com.lab.greenpremium.data.network.CallbackListener
import com.lab.greenpremium.data.network.ResponseError
import com.lab.greenpremium.utills.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
                        { listener.onError(it) }
                )
    }

    @SuppressLint("CheckResult")
    fun getContacts(listener: CallbackListener) {
        apiMethods.getContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response -> handleResponse(response, listener) },
                        { listener.onError(it) }
                )
    }

    private inline fun <reified T> handleResponse(response: BaseResponse<T>, listener: CallbackListener) {
        LogUtil.i(response.toString())
        if (response.status == 200) {
            when (T::class) {

                AuthData::class -> {
                    val authData = response.data as AuthData
                    UserModel.authData = authData
                    preferences.setToken(authData.token)
                }

                Contacts::class -> {
                    UserModel.contacts = response.data as Contacts
                }
            }

            listener.onSuccess()

        } else {
            listener.onError(ResponseError(response.status, response.title))
        }
    }
}