package com.lab.greenpremium.data.repository

import com.lab.greenpremium.data.entity.AuthData
import com.lab.greenpremium.data.entity.BaseResponse
import com.lab.greenpremium.data.local.PreferencesManager
import com.lab.greenpremium.data.network.CallbackListener
import com.lab.greenpremium.data.network.ResponseError
import com.lab.greenpremium.utills.LogUtil
import javax.inject.Inject


open class BaseRepository @Inject constructor(val preferences: PreferencesManager) {

    protected inline fun <reified T> handleResponse(response: BaseResponse<T>, listener: CallbackListener) {
        LogUtil.i(response.toString())

        if (response.status == 200) {

            when (T::class) {

                AuthData::class -> {
                    val authData = response.data as AuthData
                    UserModel.authData = authData
                    preferences.setToken(authData.token)
                }

            }

            listener.onSuccess()

        } else {
            listener.onError(ResponseError(response.status, response.title))
        }
    }

}
