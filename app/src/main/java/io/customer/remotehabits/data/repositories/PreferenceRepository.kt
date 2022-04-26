package io.customer.remotehabits.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import io.customer.remotehabits.BuildConfig
import io.customer.remotehabits.data.models.Workspace
import io.customer.remotehabits.utils.PreferencesKeys.API_KEY
import io.customer.remotehabits.utils.PreferencesKeys.SITE_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PreferenceRepository {
    suspend fun saveWorkspaceCredentials(siteId: String, apiKey: String)
    fun getWorkspaceCredentials(): Flow<Workspace>
}

class PreferenceRepositoryImp(private val dataStore: DataStore<Preferences>) :
    PreferenceRepository {

    override suspend fun saveWorkspaceCredentials(siteId: String, apiKey: String) {
        dataStore.edit { preferences ->
            preferences[SITE_ID] = siteId
            preferences[API_KEY] = apiKey
        }
    }

    override fun getWorkspaceCredentials(): Flow<Workspace> {
        return dataStore.data.map { preferences ->
            return@map Workspace(
                siteId = preferences[SITE_ID] ?: BuildConfig.SITE_ID,
                apiKey = preferences[API_KEY] ?: BuildConfig.API_KEY
            )
        }
    }
}
