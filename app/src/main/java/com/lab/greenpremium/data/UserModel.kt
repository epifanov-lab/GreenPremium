package com.lab.greenpremium.data

import com.lab.greenpremium.data.entity.AuthData
import com.lab.greenpremium.data.entity.Contacts
import com.lab.greenpremium.data.entity.raw.Plant
import com.lab.greenpremium.utills.getMockPlantList


object UserModel {

    lateinit var authData: AuthData
    lateinit var contacts: Contacts

    //TODO move to another repo
    val plants: List<Plant> = getMockPlantList()

    fun getCountOfItemsInCart(): Int {
        return plants.filter { it.count > 0 }.size
    }

}