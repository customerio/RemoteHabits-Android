package io.customer.remotehabits.appinitializers

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.customer.messaginginapp.MessagingInAppModuleConfig
import io.customer.messaginginapp.ModuleMessagingInApp
import io.customer.messaginginapp.type.InAppEventListener
import io.customer.messaginginapp.type.InAppMessage
import io.customer.messagingpush.ModuleMessagingPushFCM
import io.customer.remotehabits.BuildConfig
import io.customer.remotehabits.utils.Logger
import io.customer.remotehabits.utils.PreferencesKeys.API_KEY
import io.customer.remotehabits.utils.PreferencesKeys.SITE_ID
import io.customer.remotehabits.utils.PreferencesKeys.TRACK_API_URL_KEY
import io.customer.sdk.CustomerIO
import io.customer.sdk.util.CioLogLevel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CustomerIOInitializer @Inject constructor(private val dataStore: DataStore<Preferences>, private val logger: Logger) :
    AppInitializer {
    override fun init(application: Application) {
        val (trackingApiUrl, siteId, apiKey) = runBlocking {
            return@runBlocking try {
                listOf(
                    dataStore.data.first()[TRACK_API_URL_KEY],
                    dataStore.data.first()[SITE_ID],
                    dataStore.data.first()[API_KEY]
                )
            } catch (e: Exception) {
                listOf(null, BuildConfig.SITE_ID, BuildConfig.API_KEY)
            }
        }

        CustomerIO.Builder(
            siteId = siteId ?: BuildConfig.SITE_ID,
            apiKey = apiKey ?: BuildConfig.API_KEY,
            appContext = application
        ).apply {
            trackingApiUrl?.let { setTrackingApiURL(trackingApiUrl = it) }
            addCustomerIOModule(ModuleMessagingInApp(
                organizationId = BuildConfig.ORGANIZATION_ID,
                config = MessagingInAppModuleConfig.Builder()
                    .setEventListener(object : InAppEventListener {
                        override fun errorWithMessage(message: InAppMessage) {
                            logger.v("error with in-app message. message: $message")
                        }

                        override fun messageActionTaken(
                            message: InAppMessage,
                            currentRoute: String,
                            action: String,
                            name: String
                        ) {
                            logger.v("in-app message action taken. current route: $currentRoute, action: $action, name: $name, message: $message")
                        }

                        override fun messageDismissed(message: InAppMessage) {
                            logger.v("in-app message dismissed. message: $message")
                        }

                        override fun messageShown(message: InAppMessage) {
                            logger.v("in-app message shown. message: $message")
                        }
                    })
                    .build()))
            addCustomerIOModule(ModuleMessagingPushFCM())
            setLogLevel(CioLogLevel.DEBUG)
            build()
        }
    }
}
