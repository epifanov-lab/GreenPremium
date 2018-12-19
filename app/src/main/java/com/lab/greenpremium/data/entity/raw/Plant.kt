package com.lab.greenpremium.data.entity.raw


data class Plant(val name: String,
                 val info1: String,
                 val info2: String,
                 val price: Double,
                 val discount: Double,
                 val type: Int,
                 val drawableResId: Int? = null, //todo в будущем URL картинки
                 var count: Int = 0,
                 var isFavorite: Boolean = false) {

}