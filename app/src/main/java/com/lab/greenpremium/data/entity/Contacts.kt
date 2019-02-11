package com.lab.greenpremium.data.entity


data class ContactsResponse(val office: Contact,
                            val managers: MutableList<Contact>?) {

    val time: Long = System.currentTimeMillis()

    fun getManagers(useOffice: Boolean = false): MutableList<Contact> {
        val result: MutableList<Contact> = mutableListOf()
        if (managers != null) result.addAll(managers)
        if (useOffice) result.add(office)
        return result
    }

    fun getManagersAvailableForMeeting(): MutableList<Contact> {
        val result: MutableList<Contact> = ArrayList()
        managers?.forEach { if (it.is_meeting_available) result.add(it) }
        return result
    }
}

data class Contact(val id: String,
                   val name: String,
                   val email: String,
                   val phone: String,
                   val position: String,
                   val photo: String,
                   val is_meeting_available: Boolean,
                   val schedule: String)