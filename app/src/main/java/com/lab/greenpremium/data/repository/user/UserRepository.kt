package com.lab.greenpremium.data.repository.user

import com.lab.greenpremium.data.entity.Plant
import com.lab.greenpremium.data.repository.BaseRepository
import com.lab.greenpremium.utills.getMockPlantList


object UserRepository : BaseRepository() {

    val plants: List<Plant> = getMockPlantList()

    fun getCountOfItemsInCart() : Int {
        return plants.filter { it.count > 0 }.size
    }

}