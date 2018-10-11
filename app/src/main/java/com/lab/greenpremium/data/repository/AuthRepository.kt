package com.lab.greenpremium.data.repository

import com.lab.greenpremium.data.entity.AuthRequest
import com.lab.greenpremium.data.local.PreferencesManager
import com.lab.greenpremium.data.network.CallbackListener
import com.lab.greenpremium.data.network.ApiMethods
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class AuthRepository @Inject constructor(private val apiMethods: ApiMethods,
                                         preferences: PreferencesManager) : BaseRepository(preferences) {

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
}