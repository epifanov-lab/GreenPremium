package com.lab.greenpremium.data.repository

import com.lab.greenpremium.data.entity.AuthRequest
import com.lab.greenpremium.data.network.CallbackListener
import com.lab.greenpremium.data.network.GpApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class AuthRepository @Inject constructor(private val api: GpApi) : BaseRepository() {

    fun auth(login: String, password: String, listener: CallbackListener) {
        api.auth(AuthRequest(login, password))
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