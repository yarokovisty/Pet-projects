package org.yarokovisty.delivery.common.auth.domain.repository

interface AuthRepository {

    suspend fun saveToken(token: String)

    suspend fun getToken(): String?

    suspend fun clearToken()
}
