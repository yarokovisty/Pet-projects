package org.yarokovisty.delivery.common.profile.main.data.datasource

import io.ktor.client.HttpClient
import org.yarokovisty.delivery.common.profile.main.data.model.UserRequest
import org.yarokovisty.delivery.common.profile.main.data.model.UserSessionResponse
import org.yarokovisty.delivery.core.network.client.get
import org.yarokovisty.delivery.core.network.client.patch

internal class UserRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun getUser(token: String): UserSessionResponse =
        httpClient.get("/api/users/session", token)

    suspend fun updateUser(request: UserRequest, token: String): UserSessionResponse =
        httpClient.patch(
            url = "/api/users/profile",
            request = request,
            token = token
        )
}
