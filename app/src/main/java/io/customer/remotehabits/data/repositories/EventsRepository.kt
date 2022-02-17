package io.customer.remotehabits.data.repositories

import io.customer.sdk.CustomerIO

interface EventsRepository {
    fun identify(identifier: String, attributes: Map<String, Any>)
    fun track(name: String, attributes: Map<String, Any> = emptyMap())
    fun registerDeviceToken(deviceToken: String)
    fun clearIdentify()
    fun screen(name: String, attributes: Map<String, Any> = emptyMap())
}

internal class CustomerIOEventsRepositoryImp : EventsRepository {

    override fun identify(identifier: String, attributes: Map<String, Any>) {
        CustomerIO.instance().identify(identifier = identifier, attributes = attributes).enqueue()
    }

    override fun track(name: String, attributes: Map<String, Any>) {
        CustomerIO.instance().track(name = name, attributes = attributes).enqueue()
    }

    override fun registerDeviceToken(deviceToken: String) {
        CustomerIO.instance().registerDeviceToken(deviceToken = deviceToken).enqueue()
    }

    override fun clearIdentify() {
        CustomerIO.instance().clearIdentify()
    }

    override fun screen(name: String, attributes: Map<String, Any>) {
        CustomerIO.instance().screen(name = name, attributes = attributes).enqueue()
    }
}
