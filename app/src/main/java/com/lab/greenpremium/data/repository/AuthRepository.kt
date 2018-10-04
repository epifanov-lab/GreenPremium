package com.lab.greenpremium.data.repository

import com.lab.greenpremium.data.entity.AuthRequest
import com.lab.greenpremium.data.network.GpApi
import com.lab.greenpremium.utills.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class AuthRepository @Inject constructor(private val api: GpApi) : BaseRepository() {

    fun auth(login: String, password: String) {
        api.auth(AuthRequest(login, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { LogUtil.i("doOnSubscribe") }
                .doFinally { LogUtil.i("doOnSubscribe") }
                .subscribe(
                        { response -> LogUtil.i(response.toString()) },
                        { LogUtil.e(it.toString())}
                )
    }
}