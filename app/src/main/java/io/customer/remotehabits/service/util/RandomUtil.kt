package io.customer.remotehabits.service.util

import io.customer.remotehabits.extensions.random

/**
 * Exists so we can manipulate random values in tests to make them not random.
 */
interface RandomUtil {
    fun randomString(): String
    fun randomString(length: Int): String
    fun randomInt(): Int
    fun randomInt(min: Int, max: Int): Int
}

class AppRandomUtil : RandomUtil {

    override fun randomString() = String.random
    override fun randomString(length: Int) = String.random(length)
    override fun randomInt() = Int.random
    override fun randomInt(min: Int, max: Int) = Int.random(min, max)
}
