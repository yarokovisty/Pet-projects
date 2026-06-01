package org.yarokovisty.delivery.common.auth.data.repository

import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.core.network.token.TokenProvider

internal class AuthRepositoryImpl(
    private val tokenProvider: TokenProvider,
) : AuthRepository {

    override suspend fun saveToken(token: String) {
        tokenProvider.set(token)
    }

    override suspend fun getToken(): String? =
        tokenProvider.get()

    override suspend fun clearToken() {
        tokenProvider.clear()
    }
}
