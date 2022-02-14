package io.customer.remotehabits

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import io.customer.sdk.CustomerIO
import timber.log.Timber.*
import timber.log.Timber.Forest.plant

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // always call this before initializing firebase
        val builder = CustomerIO.Builder(
            siteId = BuildConfig.SITE_ID,
            apiKey = BuildConfig.API_KEY,
            appContext = this
        )
        builder.build()

        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        }

        FirebaseApp.initializeApp(this)
        // manually enable firebase messaging and analytics. We disabled them from starting automatically in the manifest so that way they don't run during tests (even though it's not a huge deal if they do).
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }
}
