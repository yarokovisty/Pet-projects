package org.yarokovisty.delivery.core.network.token

import org.yarokovisty.delivery.core.storage.encryption.EncryptedPreferencesStorage

interface TokenProvider {

    suspend fun get(): String?

    suspend fun set(token: String)

    suspend fun clear()
}

internal class TokenProviderImpl(private val encryptedStorage: EncryptedPreferencesStorage) : TokenProvider {

    private companion object {

        const val TOKEN_KEY = "token"
    }

    override suspend fun get(): String? =
        encryptedStorage.getDecryptedString(TOKEN_KEY)

    override suspend fun set(token: String) {
        encryptedStorage.putEncryptedString(TOKEN_KEY, token)
    }

    override suspend fun clear() {
        encryptedStorage.remove(TOKEN_KEY)
    }
}
