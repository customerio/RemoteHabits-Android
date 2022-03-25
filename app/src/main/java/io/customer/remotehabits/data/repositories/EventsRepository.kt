package io.customer.remotehabits.data.repositories

import androidx.annotation.WorkerThread
import io.customer.base.comunication.Action
import io.customer.base.data.ErrorResult
import io.customer.base.data.Result
import io.customer.remotehabits.utils.Logger
import io.customer.sdk.CustomerIO

interface EventsRepository {
    fun identify(identifier: String, attributes: Map<String, Any>)
    fun track(name: String, attributes: Map<String, Any> = emptyMap())
    fun registerDeviceToken(deviceToken: String)
    fun clearIdentify()
    fun screen(name: String, attributes: Map<String, Any> = emptyMap())
}

internal class CustomerIOEventsRepositoryImp(val logger: Logger) :
    EventsRepository {

    private val logCallback = Action.Callback<Unit> { result ->
        if (result.isSuccess) {
            logger.v("success")
        } else {
            logger.v((result as ErrorResult).error.cause)
        }
    }

    private fun logAction(action: Result<Unit>) {
        if (action.isSuccess) {
            logger.v("success")
        } else {
            logger.v((action as ErrorResult).error.getDisplayMessage())
        }
    }

    @WorkerThread
    override fun identify(identifier: String, attributes: Map<String, Any>) {
        logger.v("identify")
        CustomerIO.instance().identify(identifier = identifier, attributes = attributes).execute()
            .also {
                logAction(it)
            }
    }

    override fun track(name: String, attributes: Map<String, Any>) {
        logger.v("track")
        CustomerIO.instance().track(name = name, attributes = attributes).enqueue(logCallback)
    }

    override fun registerDeviceToken(deviceToken: String) {
        logger.v("registerDeviceToken")
        CustomerIO.instance().registerDeviceToken(deviceToken = deviceToken).enqueue(logCallback)
    }

    override fun clearIdentify() {
        logger.v("clearIdentify")
        CustomerIO.instance().clearIdentify()
    }

    override fun screen(name: String, attributes: Map<String, Any>) {
        logger.v("screen")
        CustomerIO.instance().screen(name = name, attributes = attributes).enqueue(logCallback)
    }
}
