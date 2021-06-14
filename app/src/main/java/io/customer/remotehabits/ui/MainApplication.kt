package io.customer.remotehabits.ui

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        // manually enable firebase messaging and analytics. We disabled them from starting automatically in the manifest so that way they don't run during tests (even though it's not a huge deal if they do).
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }
}
