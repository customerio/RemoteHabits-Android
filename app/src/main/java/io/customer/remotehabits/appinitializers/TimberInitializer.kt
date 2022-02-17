package io.customer.remotehabits.appinitializers

import android.app.Application
import io.customer.remotehabits.BuildConfig
import io.customer.remotehabits.utils.Logger
import javax.inject.Inject

class TimberInitializer @Inject constructor(
    private val logger: Logger
) : AppInitializer {
    override fun init(application: Application) = logger.setup(BuildConfig.DEBUG)
}
