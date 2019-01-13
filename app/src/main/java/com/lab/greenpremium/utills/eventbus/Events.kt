package com.lab.greenpremium.utills.eventbus

import com.lab.greenpremium.data.entity.Product

abstract class BaseEvent

class ProductQuantityChangedEvent(val product: Product) : BaseEvent()
class CartUpdatedEvent : BaseEvent()