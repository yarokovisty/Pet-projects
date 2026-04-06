package org.yarokovisty.delivery.core.storage.encryption

import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage
import org.yarokovisty.delivery.libs.encryption.StringEncryptor

internal class EncryptedStringStorageImpl(
    private val preferencesStorage: PreferencesStorage,
    private val encryptor: StringEncryptor,
) : EncryptedStringStorage {

    override suspend fun putEncryptedString(key: String, value: String?) {
        if (value == null) {
            preferencesStorage.remove(key)
        } else {
            val encrypted = encryptor.encrypt(value)
            preferencesStorage.putString(key, encrypted)
        }
    }

    override suspend fun getDecryptedString(key: String, default: String?): String? {
        val encrypted = preferencesStorage.getString(key, default = null) ?: return default
        return encryptor.decrypt(encrypted) ?: default
    }
}
