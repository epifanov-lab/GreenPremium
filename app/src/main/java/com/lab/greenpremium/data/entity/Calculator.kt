package com.lab.greenpremium.data.entity

import java.io.Serializable


//post /calc/service
data class CalcServiceRequest(
        var plants_count_s1: Int = 0, var pots_count_s1: Int = 0,
        var plants_count_s2: Int = 0, var pots_count_s2: Int = 0,
        var plants_count_s3: Int = 0, var pots_count_s3: Int = 0,
        var plants_count_s4: Int = 0, var pots_count_s4: Int = 0,
        var plants_count_s5: Int = 0, var pots_count_s5: Int = 0
) {
    fun isChanged(): Boolean {
        return plants_count_s1 != 0 || pots_count_s1 != 0 ||
        plants_count_s2 != 0 || pots_count_s2 != 0 ||
        plants_count_s3 != 0 || pots_count_s3 != 0 ||
        plants_count_s4 != 0 || pots_count_s4 != 0 ||
        plants_count_s5 != 0 || pots_count_s5 != 0
    }
}

data class CalcServiceResponse(val title: String, val message: String) : Serializable