package org.yarokovisty.delivery.feature.login.impl.data.datasource

import io.ktor.client.HttpClient
import org.yarokovisty.delivery.core.network.client.post
import org.yarokovisty.delivery.feature.login.impl.data.model.OtpRequest
import org.yarokovisty.delivery.feature.login.impl.data.model.OtpResponse
import org.yarokovisty.delivery.feature.login.impl.data.model.SigninRequest
import org.yarokovisty.delivery.feature.login.impl.data.model.SigninResponse

internal class LoginRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun otp(request: OtpRequest): OtpResponse =
        httpClient.post("/api/auth/otp", request)

    suspend fun signin(request: SigninRequest): SigninResponse =
        httpClient.post("/api/users/signin", request)
}
