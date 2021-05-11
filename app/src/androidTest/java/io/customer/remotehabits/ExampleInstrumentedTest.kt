package io.customer.remotehabits

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.squareup.moshi.JsonClass
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.customer.remotehabits.di.NetworkModule
import io.customer.remotehabits.mock.MockWebServer
import io.customer.remotehabits.util.TestSetupUtil
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import org.junit.rules.ExternalResource

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
class ExampleInstrumentedTest: BaseInstrumentationTest() {

    override fun provideTestClass(): Any = this

    @Inject lateinit var mockWebServer: MockWebServer

    @Test
    fun useAppContext() {
        mockWebServer.queue(200, listOf(FooVo("name")).toTypedArray())

        assertEquals("io.customer.remotehabits", context.packageName)
    }

}

@JsonClass(generateAdapter = true)
class FooVo(val name: String)
