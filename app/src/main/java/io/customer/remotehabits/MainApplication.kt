package io.customer.remotehabits

import android.app.Application
import android.net.Uri
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import io.customer.sdk.CustomerIO
import io.customer.sdk.data.communication.CustomerIOUrlHandler

@HiltAndroidApp
class MainApplication : Application(), CustomerIOUrlHandler {

    override fun onCreate() {
        super.onCreate()

        // always call this before initializing firebase
        val builder = CustomerIO.Builder(
            siteId = BuildConfig.SITE_ID,
            apiKey = BuildConfig.API_KEY,
            appContext = this
        )
        builder.setCustomerIOUrlHandler(this)
        builder.build()

        FirebaseApp.initializeApp(this)
        // manually enable firebase messaging and analytics. We disabled them from starting automatically in the manifest so that way they don't run during tests (even though it's not a huge deal if they do).
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }

    override fun handleCustomerIOUrl(uri: Uri): Boolean {
        Log.v("handleIterableURL", uri.toString())

        // since we dont have a Deep link manager here, returning false
        return false
    }
}
