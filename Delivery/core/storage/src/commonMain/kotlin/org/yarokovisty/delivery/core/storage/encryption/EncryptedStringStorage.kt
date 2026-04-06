package org.yarokovisty.delivery.core.storage.encryption

interface EncryptedStringStorage {
    suspend fun putEncryptedString(key: String, value: String?)
    suspend fun getDecryptedString(key: String, default: String? = null): String?
}
