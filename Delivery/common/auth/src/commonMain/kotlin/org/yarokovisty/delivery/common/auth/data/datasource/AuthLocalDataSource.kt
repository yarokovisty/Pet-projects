package org.yarokovisty.delivery.common.auth.data.datasource

import org.yarokovisty.delivery.core.storage.encryption.EncryptedPreferencesStorage

internal class AuthLocalDataSource(
    private val encryptedStorage: EncryptedPreferencesStorage
) {

    private companion object {

        const val TOKEN_KEY = "token"
    }

    suspend fun saveToken(token: String) {
        encryptedStorage.putEncryptedString(TOKEN_KEY, token)
    }

    suspend fun getToken(): String? =
        encryptedStorage.getDecryptedString(TOKEN_KEY)

    suspend fun clearToken() {
        encryptedStorage.remove(TOKEN_KEY)
    }
}
