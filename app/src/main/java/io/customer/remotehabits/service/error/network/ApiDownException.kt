package io.customer.remotehabits.service.error.network

/**
 * If the API service is down (>= 500 status code from server)
 */
class ApiDownException(message: String): Throwable(message)