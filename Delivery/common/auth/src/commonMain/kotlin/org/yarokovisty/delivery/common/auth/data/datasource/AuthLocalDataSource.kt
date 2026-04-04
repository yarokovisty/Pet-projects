package org.yarokovisty.delivery.common.auth.data.datasource

import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage

internal class AuthLocalDataSource(
    private val storage: PreferencesStorage
) {

    private companion object {

        const val TOKEN_KEY = "token"
    }

    suspend fun saveToken(token: String) {
        storage.putString(TOKEN_KEY, token)
    }

    suspend fun getToken(): String? =
        storage.getString(TOKEN_KEY)

    suspend fun clearToken() {
        storage.remove(TOKEN_KEY)
    }
}
