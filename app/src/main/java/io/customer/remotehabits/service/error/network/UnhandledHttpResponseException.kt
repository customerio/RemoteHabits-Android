package io.customer.remotehabits.service.error.network

import io.customer.remotehabits.service.api.ProcessedResponse

/**
 * an http request was successful (a status code was received) but the code did not handle the response.
 */
class UnhandledHttpResponseException(response: ProcessedResponse<*>): Throwable("Unhandled HTTP response. (${response.statusCode}) ${response.method} ${response.url}, ${response.body?.toString() ?: "(no body)"}")