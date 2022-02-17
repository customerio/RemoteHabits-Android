package io.customer.remotehabits.appinitializers

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject

class FirebaseMessagingInitializer @Inject constructor() : AppInitializer {

    override fun init(application: Application) {
        FirebaseApp.initializeApp(application)
        // manually enable firebase messaging and analytics. We disabled them from starting automatically in the manifest so that way they don't run during tests (even though it's not a huge deal if they do).
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }
}
