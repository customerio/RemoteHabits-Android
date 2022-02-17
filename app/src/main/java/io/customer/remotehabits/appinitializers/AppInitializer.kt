package io.customer.remotehabits.appinitializers

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}
