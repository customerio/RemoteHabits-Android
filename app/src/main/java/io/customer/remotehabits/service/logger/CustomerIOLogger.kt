package io.customer.remotehabits.service.logger

import android.os.Bundle
import java.util.*

class CustomerIOLogger : Logger {

    override fun setUserId(id: String?) {
        // call SDK
    }

    override fun appActivityOccurred(event: AppActivityKey, extras: Map<AppActivityParamKey, Any>?) {
        // call SDK
    }

    override fun setUserAttribute(key: UserAttributeKey, value: String) {
        // call SDK
    }

    override fun breadcrumb(caller: Any, event: String, extras: Bundle?) {
    }

    override fun httpRequestEvent(method: String, url: String, reqBody: String?) {
    }

    override fun httpSuccessEvent(method: String, url: String, code: Int, reqHeaders: String?, resHeaders: String?, resBody: String?) {
    }

    override fun httpFailEvent(method: String, url: String, code: Int, reqHeaders: String?, resHeaders: String?, resBody: String?) {
    }

    override fun errorOccurred(error: Throwable) {
    }
}
