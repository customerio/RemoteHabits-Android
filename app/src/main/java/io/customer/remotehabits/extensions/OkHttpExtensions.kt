package io.customer.remotehabits.extensions

import io.customer.remotehabits.service.json.JsonAdapter
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

// We can only call response.body().string() *once* from Okhttp because of it trying to be conservative to memory. So, we must create a copy of the body. https://github.com/square/okhttp/issues/1240#issuecomment-330813274
fun Response.getBodyCopy(): String? {
    return if (body != null) {
        val responseBodyCopy = peekBody(java.lang.Long.MAX_VALUE)
        val responseBody = responseBodyCopy.string()

        responseBody
    } else {
        null
    }
}

object Response {
    fun <HttpResponseError, T> error(code: Int, body: T): retrofit2.Response<HttpResponseError> {
        return retrofit2.Response.error(code, JsonAdapter.toJson(body as Any).toResponseBody("application/json".toMediaType()))
    }
}
