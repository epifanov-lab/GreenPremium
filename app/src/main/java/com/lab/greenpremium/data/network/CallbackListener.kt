package com.lab.greenpremium.data.network


interface CallbackListener {

    fun doBefore()
    fun doAfter()
    fun onError(throwable: Throwable)
    fun onSuccess()

}