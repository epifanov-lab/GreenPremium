package com.lab.greenpremium.data.network


class ApiError(status: Int, val title: String) : Throwable("Error $status: $title")