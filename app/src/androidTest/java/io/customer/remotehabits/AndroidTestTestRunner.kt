package io.customer.remotehabits

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Needed for...
 * 1. Allow Hilt to perform dependency injection in the Android app under test and the test class. https://dagger.dev/hilt/instrumentation-testing
 */
class AndroidTestTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }

}