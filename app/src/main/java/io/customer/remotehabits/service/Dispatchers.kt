package io.customer.remotehabits.service

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Way to differentiate Dispatchers (IO vs Main, for example). There needs to be a way to differentiate for dependency injection.
 */
class Dispatchers(val io: CoroutineDispatcher)