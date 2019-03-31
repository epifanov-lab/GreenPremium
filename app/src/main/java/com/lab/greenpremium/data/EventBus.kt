package com.lab.greenpremium.data

import com.lab.greenpremium.data.entity.Product

abstract class BaseEvent

class MeetingAddedEvent : BaseEvent()
class ServiceCalculatedEvent : BaseEvent()
class MessageSentEvent : BaseEvent()

class ProductQuantityChangedEvent(val product: Product) : BaseEvent()

class CartUpdatedEvent : BaseEvent()