package org.yarokovisty.delivery.core.storage.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

internal class PreferencesStorageImpl(
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

    override suspend fun getBoolean(key: String, default: Boolean?): Boolean? {
        val prefs = dataStore.data.first()
        return prefs[booleanPreferencesKey(key)] ?: default
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        dataStore.edit { prefs ->
            val prefsKey = booleanPreferencesKey(key)
            prefs[prefsKey] = value
        }
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
