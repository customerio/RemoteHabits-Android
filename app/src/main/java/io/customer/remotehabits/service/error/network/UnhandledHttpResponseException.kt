package io.customer.remotehabits.service.error.network

/**
 * an http request was successful (a status code was received) but the code did not handle the response.
 */
class UnhandledHttpResponseException(message: String): Throwable(message)