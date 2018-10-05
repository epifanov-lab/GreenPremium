package com.lab.greenpremium.data.network


class ResponseError(val errorCode: Int, message: String): Throwable("Error $errorCode: $message")