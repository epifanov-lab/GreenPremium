package com.lab.greenpremium.utills

import com.lab.greenpremium.data.entity.Plant


fun getMockPlantList(enableSelector : Boolean = false) : List<Plant> {
    val result : ArrayList<Plant> = ArrayList()

    for (i in 1..50) {
        val name = "Аглаонема #$i"
        val info1 = "Размер кашпо 150*150*150 см"
        val info2 = "Высота композиции ${i * 100} мм"
        val price = Math.random() * 100000
        val discount = Math.random() * 100000 + 100000
        val type = if (enableSelector) Plant.Type.BIG else Plant.Type.LIVING
        result.add(Plant(name, info1, info2, price, discount, type))
    }

    return result
}