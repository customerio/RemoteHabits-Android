package io.customer.remotehabits.service.logger

import android.os.Bundle
import android.util.Log
import io.customer.remotehabits.Env
import java.util.*

class LogcatLogger : Logger {

    companion object {
        val TAG = Env.appName.toUpperCase(Locale.ENGLISH)
    }

    override fun setUserId(id: String?) {
        if (id == null) Log.d(TAG, "User logged out")
        else Log.d(TAG, "User logged in. id $id")
    }

    override fun appActivityOccurred(event: AppActivityKey, extras: Map<AppActivityParamKey, Any>?) {
        Log.d(TAG, "event: ${event.name}, extras: $extras")
    }

    override fun setUserAttribute(key: UserAttributeKey, value: String) {
        Log.d(TAG, "Property, ${key.name}:$value")
    }

    override fun breadcrumb(caller: Any, event: String, extras: Bundle?) {
        Log.d(TAG, "$event (from: ${caller::class.java.simpleName}) extras: $extras")
    }

    override fun httpRequestEvent(method: String, url: String, reqBody: String?) {
        Log.d(TAG, "Http ------>>> $method $url, req body: ${reqBody ?: "(none)"}", null)
    }

    override fun httpSuccessEvent(method: String, url: String, code: Int, reqHeaders: String?, resHeaders: String?, resBody: String?) {
        Log.d(TAG, "Http <<<------ $method $url, code: $code, res body: ${resBody ?: "(none)"}", null)
    }

    override fun httpFailEvent(method: String, url: String, code: Int, reqHeaders: String?, resHeaders: String?, resBody: String?) {
        Log.d(TAG, "Http Response Failed! method: method: $method, url: $url, code: $code, req headers: ${reqHeaders ?: "(none)"}, res headers: ${resHeaders ?: "(none)"}, res body: ${resBody ?: "(none)"}", null)
    }

    override fun errorOccurred(error: Throwable) {
        Log.e(TAG, "ERROR: ${error.message}", error)

        error.printStackTrace()
    }
}