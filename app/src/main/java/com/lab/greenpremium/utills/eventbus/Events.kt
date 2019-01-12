package com.lab.greenpremium.utills.eventbus

import com.lab.greenpremium.data.entity.Product

abstract class BaseEvent

class CartChangedEvent(val product: Product) : BaseEvent()