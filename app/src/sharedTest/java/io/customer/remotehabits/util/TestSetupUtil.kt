package io.customer.remotehabits.util

import android.content.SharedPreferences
import io.customer.remotehabits.mock.MockWebServer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestSetupUtil @Inject constructor(
    private val mockWebServer: MockWebServer
) {

    /**
     * Run from background thread. Prefer in the startup of your tests.
     */
    fun setup() {
        assert(ThreadUtil.isBackgroundThread)

        mockWebServer.start()
    }

    fun teardown() {
        mockWebServer.stop()
    }
}