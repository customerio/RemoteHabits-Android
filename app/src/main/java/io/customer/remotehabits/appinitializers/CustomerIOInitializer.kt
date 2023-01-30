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
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

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
            addCustomerIOModule(ModuleMessagingPushFCM())
            setLogLevel(CioLogLevel.DEBUG)
            build()
        }
    }

    private fun trackInAppEvent(
        eventName: String,
        message: InAppMessage,
        arguments: Map<String, Any> = emptyMap()
    ) = CustomerIO.instance().track(
        name = "In-App Event",
        attributes = arguments.plus(
            mapOf(
                "event_name" to eventName,
                "message_id" to message.messageId,
                "delivery_id" to (message.deliveryId ?: "NULL")
            )
        )
    )
}
