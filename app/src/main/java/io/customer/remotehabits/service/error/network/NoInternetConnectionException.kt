package io.customer.remotehabits.service.error.network

import java.io.IOException

/**
 * the mobile device is not connected to the Internet.
 *
 * Note: subclassing [IOException] because that's the only way that retrofit will allow our code to try/catch when exceptions happen in coroutines. If we subclassed something else like [Throwable], our coroutine would throw a fatal app crash no matter how we catch exceptions in coroutines. See: https://stackoverflow.com/q/58697459
 */
class NoInternetConnectionException(message: String) : IOException(message)
