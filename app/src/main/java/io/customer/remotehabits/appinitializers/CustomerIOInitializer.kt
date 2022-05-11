package io.customer.remotehabits.appinitializers

import android.app.Application
import io.customer.remotehabits.BuildConfig
import io.customer.sdk.CustomerIO
import io.customer.sdk.util.CioLogLevel
import javax.inject.Inject

class CustomerIOInitializer @Inject constructor() : AppInitializer {
    override fun init(application: Application) {
        CustomerIO.Builder(
            siteId = BuildConfig.SITE_ID,
            apiKey = BuildConfig.API_KEY,
            appContext = application
        )
            .setLogLevel(CioLogLevel.DEBUG)
            .build()
    }
}
