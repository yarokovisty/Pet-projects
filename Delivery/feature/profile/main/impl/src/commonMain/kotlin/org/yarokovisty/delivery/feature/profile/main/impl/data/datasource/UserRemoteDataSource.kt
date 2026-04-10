package org.yarokovisty.delivery.feature.profile.main.impl.data.datasource

import io.ktor.client.HttpClient
import org.yarokovisty.delivery.core.network.client.get
import org.yarokovisty.delivery.core.network.client.patch
import org.yarokovisty.delivery.feature.profile.main.impl.data.model.UserRequest
import org.yarokovisty.delivery.feature.profile.main.impl.data.model.UserSessionResponse

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
