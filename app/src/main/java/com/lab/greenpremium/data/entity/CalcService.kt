package com.lab.greenpremium.data.entity


//post /calc/service
data class CalcServiceRequst(
        val plants_count_s1: Int, val pots_count_s1: Int,
        val plants_count_s2: Int, val pots_count_s2: Int,
        val plants_count_s3: Int, val pots_count_s3: Int,
        val plants_count_s4: Int, val pots_count_s4: Int,
        val plants_count_s5: Int, val pots_count_s5: Int
)

data class CalcServiceResponse(val title: String, val message: String)