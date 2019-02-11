package com.lab.greenpremium.data

import com.lab.greenpremium.data.entity.*


object UserModel {

    var contactsResponse: ContactsResponse? = null

    var objectInfoResponse: ObjectInfoResponse? = null

    var eventsResponse: EventsResponse? = null

    var orderResponse: OrderResponse? = null

    var meetingsListResponse: MeetingsListResponse? = null

    var portfolio: PortfolioResponse? = null

    var mapObjectsResponse: MapObjectsResponse? = null

    fun clear() {
        contactsResponse = null
        objectInfoResponse = null
        eventsResponse = null
        orderResponse = null
        meetingsListResponse = null
        portfolio = null
        mapObjectsResponse = null
    }

}