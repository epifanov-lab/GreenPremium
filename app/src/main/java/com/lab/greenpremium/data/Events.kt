package com.lab.greenpremium.data

abstract class BaseEvent

class MeetingAddedEvent : BaseEvent()
class ServiceCalculatedEvent : BaseEvent()

class EventsPaginationStateChanging(val enabled: Boolean) : BaseEvent()
class ProductPaginationStateChanging(val enabled: Boolean) : BaseEvent()