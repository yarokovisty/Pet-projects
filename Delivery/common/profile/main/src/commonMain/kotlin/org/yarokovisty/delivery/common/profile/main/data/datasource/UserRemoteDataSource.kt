package org.yarokovisty.delivery.common.profile.main.data.datasource

import io.ktor.client.HttpClient
import org.yarokovisty.delivery.common.profile.main.data.model.UserRequest
import org.yarokovisty.delivery.common.profile.main.data.model.UserSessionResponse
import org.yarokovisty.delivery.core.network.extenstions.get
import org.yarokovisty.delivery.core.network.extenstions.patch

internal class UserRemoteDataSource(private val authHttpClient: HttpClient) {

    suspend fun get(): UserSessionResponse =
        authHttpClient.get("/api/users/session")

    suspend fun update(request: UserRequest): UserSessionResponse =
        authHttpClient.patch(url = "/api/users/profile", request = request)
}
