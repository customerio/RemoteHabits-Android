package io.customer.remotehabits

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.customer.remotehabits.appinitializers.AppInitializers
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {

    @Inject
    lateinit var initializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
    }
}
