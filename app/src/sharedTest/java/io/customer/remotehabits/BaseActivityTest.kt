package io.customer.remotehabits

import android.app.Activity
import android.app.Instrumentation
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltTestApplication
import io.customer.remotehabits.rule.MainCoroutineRule
import io.customer.remotehabits.util.EspressoTestUtil
import org.junit.After
import org.junit.Before
import org.junit.rules.RuleChain
import org.mockito.MockitoAnnotations

/**
 * Base class for Espresso UI tests testing an Activity class.
 */
abstract class BaseActivityTest<Act : FragmentActivity>: Test() {

    protected lateinit var activityScenario: ActivityScenario<Act>
    abstract fun provideActivityClass(): Class<Act>

    protected lateinit var mockWebServer: MockWebServer

    /**
     * Test rules specific for Android instrumentation UI tests.
     *
     * Android instrumentation Espresso UI tests seem to work fine using the default app given threads. Espresso tests can use Idling Resources to avoid needing to replace threads and Espresso tests should run as close to possible to production code for testing code accurately.
     */
    protected val uiRules: RuleChain = RuleChain
        .outerRule(diRule)

    /**
     * Optional override if you want to perform anything after Activity is launched. Runs for *all* test functions.
     */
    open fun afterActivityLaunch(activity: Act) {
    }

    @Before
    override fun setup() {
        super.setup()

        /**
         * Make sure to start the mock web server before dependency injection as we need a URL to the web server to be injected into the app.
         */
        mockWebServer = MockWebServer(okhttp3.mockwebserver.MockWebServer())
        mockWebServer.start()

        // if this doesn't work, you can use the mockito test rule: MockitoJUnit.rule().
        MockitoAnnotations.initMocks(provideTestClass())

        diRule.inject()
    }

    @After
    override fun teardown() {
        super.teardown()

        activityScenario.close()

        mockWebServer.stop()
    }

    /**
     * When your test function setup is complete, time for you to start the Activity under test
     */
    protected fun launchActivity() {
        activityScenario = ActivityScenario.launch(provideActivityClass())
        activityScenario.onActivity { activity ->
            EspressoTestUtil.disableAnimations(activity)
            afterActivityLaunch(activity)
        }
    }
}