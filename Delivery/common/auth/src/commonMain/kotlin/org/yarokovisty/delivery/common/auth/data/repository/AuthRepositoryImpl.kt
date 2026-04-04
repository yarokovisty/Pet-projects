package org.yarokovisty.delivery.common.auth.data.repository

import org.yarokovisty.delivery.common.auth.data.datasource.AuthLocalDataSource
import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository

internal class AuthRepositoryImpl(
    private val localDataSource: AuthLocalDataSource
) : AuthRepository {

    override suspend fun saveToken(token: String) {
        localDataSource.saveToken(token)
    }

    override suspend fun getToken(): String? =
        localDataSource.getToken()

    override suspend fun clearToken() {
        localDataSource.clearToken()
    }
}
