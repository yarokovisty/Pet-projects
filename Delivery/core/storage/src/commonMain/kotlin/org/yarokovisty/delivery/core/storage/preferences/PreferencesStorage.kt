package org.yarokovisty.delivery.core.storage.preferences

interface PreferencesStorage {

    suspend fun getString(key: String, default: String? = null): String?
    suspend fun putString(key: String, value: String?)

    suspend fun getInt(key: String, default: Int? = null): Int?
    suspend fun putInt(key: String, value: Int?)

    suspend fun getBoolean(key: String, default: Boolean? = null): Boolean?
    suspend fun putBoolean(key: String, value: Boolean?)

    suspend fun remove(key: String)
    suspend fun clear()
}
