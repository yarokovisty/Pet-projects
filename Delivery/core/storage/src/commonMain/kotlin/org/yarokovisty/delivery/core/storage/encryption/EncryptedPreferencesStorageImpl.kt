package org.yarokovisty.delivery.core.storage.encryption

import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage
import org.yarokovisty.delivery.libs.encryption.StringEncryptor

internal class EncryptedPreferencesStorageImpl(
    private val preferencesStorage: PreferencesStorage,
    private val encryptor: StringEncryptor,
) : EncryptedPreferencesStorage {

    override suspend fun putEncryptedString(key: String, value: String) {
        val encrypted = encryptor.encrypt(value)
        preferencesStorage.putString(key, encrypted)
    }

    override suspend fun getDecryptedString(key: String): String? {
        val encrypted = preferencesStorage.getString(key, default = null) ?: return null
        return encryptor.decrypt(encrypted)
    }

    override suspend fun remove(key: String) {
        preferencesStorage.remove(key)
    }
}
