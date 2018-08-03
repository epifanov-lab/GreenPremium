package com.lab.greenpremium.data.entity

import android.content.Context
import com.lab.greenpremium.R
import java.io.Serializable


data class Plant(val name: String,
                 val info1: String,
                 val info2: String,
                 val price: Double,
                 val discount: Double,
                 val type: Type,
                 val drawableResId: Int? = null, //todo в будущем URL картинки
                 var count: Int = 0) {

    enum class Type(var titleResId: Int) : Serializable {
        LIVING(R.string.title_plants_living),
        ARTIFICIAL(R.string.title_plants_artificial),
        BIG(R.string.title_plants_big);

        companion object {

            fun getTitles(context: Context?): Array<String> {
                val titles: Array<String> = Array(Type.values().size) { "" }
                enumValues<Type>().forEach { titles[it.ordinal] = context!!.getString(it.titleResId) }
                return titles
            }

            fun fromInt(value: Int): Type {
                for (type in Type.values()) {
                    if (value == type.ordinal) {
                        return type
                    }
                }
                return LIVING
            }
        }
    }

}