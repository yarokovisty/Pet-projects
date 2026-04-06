package org.yarokovisty.delivery.core.storage.encryption

interface EncryptedPreferencesStorage {
    suspend fun putEncryptedString(key: String, value: String)
    suspend fun getDecryptedString(key: String): String?

    suspend fun remove(key: String)
}
