package io.customer.remotehabits.service.error

class DeveloperError(val originalError: Throwable, humanReadableMessage: String) : Throwable(humanReadableMessage)
