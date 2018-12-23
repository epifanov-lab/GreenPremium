package com.lab.greenpremium.data.network

import java.io.Serializable


interface CallbackListener {

    fun doBefore()
    fun doAfter()
    fun onError(throwable: Throwable)
    fun onSuccess()
    fun onSuccess(item: Serializable?)

}