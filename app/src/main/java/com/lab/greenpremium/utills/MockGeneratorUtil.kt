package com.lab.greenpremium.utills

import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Contact
import com.lab.greenpremium.data.entity.Plant


fun getMockPlantList(enableSelector : Boolean = false) : List<Plant> {
    val result : ArrayList<Plant> = ArrayList()

    for (i in 1..15) {
        val name = "Аглаонема #$i"
        val info1 = "Размер кашпо 150*150*150 см"
        val info2 = "Высота композиции ${i * 100} мм"
        val price = Math.random() * 100000
        val discount = Math.random() * 100000 + 100000
        val type = if (enableSelector) Plant.Type.BIG else Plant.Type.LIVING
        val drawableResIdMock = if (i % 3 == 0) R.drawable.dummy_plant_1 else if (i % 2 == 0) R.drawable.dummy_plant_2 else R.drawable.dummy_plant_3
        result.add(Plant(name, info1, info2, price, discount, type, drawableResId = drawableResIdMock))
    }

    return result
}

fun getMockContactList() : List<Contact> {
    val result : ArrayList<Contact> = ArrayList()

    for (i in 1..9) {
        val name = "Деракова Зоя Борисовна #$i"
        val position = "Руководитель службы сервиса"
        val mail = "jb@greenpremium.ru"
        val phone = "+ 7 (495) 994 65 4$i"
        val info = "Мы ухаживаем за вашими растениями в пн., чт., с 11:00 - 13:00"
        result.add(Contact(name, position, mail, phone, null))
    }

    return result
}