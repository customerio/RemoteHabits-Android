package io.customer.remotehabits

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ExampleInstrumentedTest: BaseInstrumentationTest() {

    override fun provideTestClass(): Any = this

    @Test
    fun useAppContext() {
        assertEquals("io.customer.remotehabits", context.packageName)
    }
}