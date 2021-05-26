package io.customer.remotehabits.service.logger

import android.os.Bundle

/**
 * Log the many activities/events that can happen within the app.
 *
 * The functions here are meant to be common between many different types of loggers. This file is meant to be generic enough that all loggers can inherit so there are no specific functions meant for 1 specific logger, as much as we can help it.
 */
interface Logger {
    fun setUserId(id: String?)

    // meant for analytics purposes
    fun appActivityOccurred(event: AppActivityKey, extras: Map<AppActivityParamKey, Any>?)
    fun setUserAttribute(key: UserAttributeKey, value: String)

    // meant for debugging purposes only.
    fun breadcrumb(caller: Any, event: String, extras: Bundle?)
    fun httpRequestEvent(method: String, url: String, reqBody: String?)
    fun httpSuccessEvent(method: String, url: String, code: Int, reqHeaders: String?, resHeaders: String?, resBody: String?)
    fun httpFailEvent(method: String, url: String, code: Int, reqHeaders: String?, resHeaders: String?, resBody: String?)
    fun errorOccurred(error: Throwable)
}

class AppLogger(private val loggers: List<Logger>) : Logger {

    override fun setUserId(id: String?) {
        loggers.forEach { it.setUserId(id) }
    }

    override fun appActivityOccurred(event: AppActivityKey, extras: Map<AppActivityParamKey, Any>?) {
        loggers.forEach { it.appActivityOccurred(event, extras) }
    }

    override fun setUserAttribute(key: UserAttributeKey, value: String) {
        loggers.forEach { it.setUserAttribute(key, value) }
    }

    override fun breadcrumb(caller: Any, event: String, extras: Bundle?) {
        loggers.forEach { it.breadcrumb(caller, event, extras) }
    }

    override fun httpRequestEvent(method: String, url: String, reqBody: String?) {
        loggers.forEach { it.httpRequestEvent(method, url, reqBody) }
    }

    override fun httpSuccessEvent(method: String, url: String, code: Int, reqHeaders: String?, resHeaders: String?, resBody: String?) {
        loggers.forEach { it.httpSuccessEvent(method, url, code, reqHeaders, resHeaders, resBody) }
    }

    override fun httpFailEvent(method: String, url: String, code: Int, reqHeaders: String?, resHeaders: String?, resBody: String?) {
        loggers.forEach { it.httpFailEvent(method, url, code, reqHeaders, resHeaders, resBody) }
    }

    override fun errorOccurred(error: Throwable) {
        loggers.forEach { it.errorOccurred(error) }
    }
}
