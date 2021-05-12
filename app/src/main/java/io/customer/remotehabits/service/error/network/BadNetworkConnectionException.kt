package io.customer.remotehabits.service.error.network

/**
 * The http network request failed for a reason that has to deal with a spotty network connection.
 */
class BadNetworkConnectionException(message: String) : Throwable(message)