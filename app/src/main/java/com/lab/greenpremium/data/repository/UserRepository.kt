package com.lab.greenpremium.data.repository

import com.lab.greenpremium.data.entity.raw.Plant
import com.lab.greenpremium.utills.getMockPlantList


object UserRepository : BaseRepository() {

    val plants: List<Plant> = getMockPlantList()

    fun getCountOfItemsInCart() : Int {
        return plants.filter { it.count > 0 }.size
    }

}