package org.yarokovisty.delivery.core.storage.preferences

import kotlinx.serialization.KSerializer

interface PreferencesStorage {

    suspend fun getString(key: String, default: String? = null): String?
    suspend fun putString(key: String, value: String)

    suspend fun getInt(key: String, default: Int? = null): Int?
    suspend fun putInt(key: String, value: Int)

    suspend fun getBoolean(key: String): Boolean?
    suspend fun putBoolean(key: String, value: Boolean)

    suspend fun <T> getObject(key: String, serializer: KSerializer<T>): T?
    suspend fun <T> putObject(key: String, value: T, serializer: KSerializer<T>)

    suspend fun remove(key: String)
    suspend fun clear()
}
