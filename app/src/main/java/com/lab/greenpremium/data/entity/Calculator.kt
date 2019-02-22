package com.lab.greenpremium.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//post /calc/service
data class CalcServiceRequest(
        @SerializedName("plants_count_s1") var plants_count_s1: Int = 0,
        @SerializedName("pots_count_s1") var pots_count_s1: Int = 0,

        @SerializedName("plants_count_s2") var plants_count_s2: Int = 0,
        @SerializedName("pots_count_s2") var pots_count_s2: Int = 0,

        @SerializedName("plants_count_s3") var plants_count_s3: Int = 0,
        @SerializedName("pots_count_s3") var pots_count_s3: Int = 0,

        @SerializedName("plants_count_s4") var plants_count_s4: Int = 0,
        @SerializedName("pots_count_s4") var pots_count_s4: Int = 0,

        @SerializedName("plants_count_s5") var plants_count_s5: Int = 0,
        @SerializedName("pots_count_s5") var pots_count_s5: Int = 0

) {
    fun isChanged(): Boolean {
        return plants_count_s1 != 0 || pots_count_s1 != 0 ||
                plants_count_s2 != 0 || pots_count_s2 != 0 ||
                plants_count_s3 != 0 || pots_count_s3 != 0 ||
                plants_count_s4 != 0 || pots_count_s4 != 0 ||
                plants_count_s5 != 0 || pots_count_s5 != 0
    }
}

data class CalcServiceResponse(@SerializedName("title") val title: String,
                               @SerializedName("message") val message: String) : Serializable