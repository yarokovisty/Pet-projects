package org.yarokovisty.delivery.core.storage.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

internal class PreferencesStorageImpl(
    private val json: Json,
    private val dataStore: DataStore<Preferences>
) : PreferencesStorage {

    override suspend fun getString(key: String, default: String?): String? {
        val prefs = dataStore.data.first()
        return prefs[stringPreferencesKey(key)] ?: default
    }

    override suspend fun putString(key: String, value: String) {
        dataStore.edit { prefs ->
            val prefsKey = stringPreferencesKey(key)
            prefs[prefsKey] = value
        }
    }

    override suspend fun getInt(key: String, default: Int?): Int? {
        val prefs = dataStore.data.first()
        return prefs[intPreferencesKey(key)] ?: default
    }

    override suspend fun putInt(key: String, value: Int) {
        dataStore.edit { prefs ->
            val prefsKey = intPreferencesKey(key)
            prefs[prefsKey] = value
        }
    }

    override suspend fun getBoolean(key: String): Boolean? {
        val prefs = dataStore.data.first()
        return prefs[booleanPreferencesKey(key)]
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        dataStore.edit { prefs ->
            val prefsKey = booleanPreferencesKey(key)
            prefs[prefsKey] = value
        }
    }

    override suspend fun <T> putObject(key: String, value: T, serializer: KSerializer<T>) {
        val string = json.encodeToString(serializer, value)
        putString(key, string)
    }

    override suspend fun <T> getObject(key: String, serializer: KSerializer<T>): T? {
        val string = getString(key) ?: return null

        return runCatching {
            json.decodeFromString(serializer, string)
        }.getOrNull()
    }

    override suspend fun remove(key: String) {
        dataStore.edit { prefs ->
            prefs.remove(stringPreferencesKey(key))
            prefs.remove(intPreferencesKey(key))
            prefs.remove(booleanPreferencesKey(key))
        }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}
