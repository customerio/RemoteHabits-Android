package io.customer.remotehabits

import android.app.Instrumentation
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.rules.RuleChain
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * Base class for running Instrumentation tests on JVM or Android devices.
 *
 * This class does the following for you:
 * 1. Contains test rules that are very common to use in test classes.
 * 2. Makes some convenient assumptions to make running tests quick/easy. Example: If you want to test a Fragment, you only need to override 1 function and the rest is done for you. But this class is also expandable and flexible so you're not locked into doing things certain ways. Feel free to override functions below to make it your own.
 */
abstract class BaseInstrumentationTest {

    /**
     * In test class, have the line: `override fun provideTestClass(): Any = this` to satisfy this
     */
    abstract fun provideTestClass(): Any

    protected val instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
    protected val context = instrumentation.targetContext
    protected val application: HiltTestApplication = instrumentation.targetContext.applicationContext as HiltTestApplication

    /**
     * Allow Hilt to construct objects in your test classes, easily. To do this, in your test class:
     * 1. Add dependencies you wish to inject: `@Inject lateinit var mockWebServer: MockWebServer`
     * 2. Add annotations to the top of your test class:
     * ```
     * @RunWith(AndroidJUnit4::class)
     * @HiltAndroidTest
     * @UninstallModules(DatabaseModule::class, NetworkModule::class) // uninstall modules if you want to use the test modules instead.
     * class FooTest: BaseInstrumentationTest() {
     * ```
     * 3. Use the test rule:
     * ```
     * @Before
     * override fun setup() {
     *   super.setup()
     *
     *   diRule.inject()
     * }
     * ```
     */
    protected val diRule = HiltAndroidRule(provideTestClass())

    /**
     * Hilt requires that test rules run in a specific order. https://developer.android.com/training/dependency-injection/hilt-testing#multiple-testrules
     * A JUnit [RuleChain] allows that to happen. Here, we have created a chain with the hilt rule already added to it. That means the Hilt rule will run first. You can add more rules to this chain in your test class.
     *
     * Use like:
     * ```
     * @get:Rule var rules = instrumentationTestRules
     * ```
     *
     * If you need to add a rule in your test class, just call [RuleChain.around] on the [instrumentationTestRules] instance in your test class.
     * ```
     * @get:Rule var rules = instrumentationTestRules.around(rule)
     * ```
     */
    protected val instrumentationTestRules: RuleChain = RuleChain
        .outerRule(diRule)

    /**
     * How to create mocks in test class with Mockito.
     *
     * This is a MethodClass for junit, not TestClass so doesn't need to be in [RuleChain].
     *
     * Use:
     * ```
     * @Mock lateinit var foo: Foo // create your mocks in your test class. They will be populated because of this test rule.
     * @get:Rule val mockito = mockitoTestRule
     * ```
     */
    protected val mockitoTestRule: MockitoRule = MockitoJUnit.rule()
}
