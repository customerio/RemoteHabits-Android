package io.customer.remotehabits

import com.nhaarman.mockitokotlin2.whenever
import io.customer.remotehabits.service.DispatcherProvider
import io.customer.remotehabits.service.api.PokeApiService
import org.junit.After
import org.junit.Before
import org.junit.rules.RuleChain
import org.mockito.MockitoAnnotations
import javax.inject.Inject

/**
 * Base class for running unit/integration tests on JVM or Android device. This is for non-UI related tests.
 */
abstract class BaseTest: Test() {

    private var testFunctionRunningCoroutine = false
    private var testFunctionDoneRunningCoroutine = false

    /**
     * Test code that launches a new coroutine. The problem with running code that launches a coroutine inside of it is that it could run asynchronously if not setup correctly. This results in possible false positives in test functions if the end of a test function gets hit but an asynchronous coroutine is still running. Use this to prevent false positives.
     *
     * ```
     * testCoroutine { done ->
     *   viewModel.getPokemon("name") { actual -> // <-- this function call will launch a new coroutine inside of it.
     *     assertThat(actual.isSuccess).isTrue()
     *
     *     done()
     *   }
     * }
     * ```
     */
    protected fun testCoroutine(run: (done: () -> Unit) -> Unit) {
        testFunctionRunningCoroutine = true
        testFunctionDoneRunningCoroutine = false

        run {
            testFunctionDoneRunningCoroutine = true
        }
    }

    // Below are some mocked (mocked in the Hilt modules) classes that you can use in integration tests.
    @Inject lateinit var dispatcherProvider: DispatcherProvider
    @Inject lateinit var pokemonApiService: PokeApiService

    @Before
    override fun setup() {
        super.setup()

        MockitoAnnotations.initMocks(provideTestClass())

        diRule.inject()

        whenever(dispatcherProvider.io()).thenReturn(mainCoroutineRule.testDispatcher)
        whenever(dispatcherProvider.main()).thenReturn(mainCoroutineRule.testDispatcher)
    }

    @After
    override fun teardown() {
        super.teardown()

        if (testFunctionRunningCoroutine) {
            if (!testFunctionDoneRunningCoroutine) throw RuntimeException("Coroutine under test is still running and has not yet been finished.")
        }
    }
}
