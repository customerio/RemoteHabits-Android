package io.customer.remotehabits.service.error.network

/**
 * the mobile device is not connected to the Internet.
 */
class NoInternetConnectionException(message: String) : Throwable(message)