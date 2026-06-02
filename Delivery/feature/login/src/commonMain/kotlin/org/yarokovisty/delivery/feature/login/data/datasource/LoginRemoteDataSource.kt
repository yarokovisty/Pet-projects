package org.yarokovisty.delivery.feature.login.data.datasource

import io.ktor.client.HttpClient
import org.yarokovisty.delivery.core.network.extenstions.post
import org.yarokovisty.delivery.feature.login.data.model.OtpRequest
import org.yarokovisty.delivery.feature.login.data.model.OtpResponse
import org.yarokovisty.delivery.feature.login.data.model.SigninRequest
import org.yarokovisty.delivery.feature.login.data.model.SigninResponse

internal class LoginRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun otp(request: OtpRequest): OtpResponse =
        httpClient.post("/api/auth/otp", request)

    suspend fun signin(request: SigninRequest): SigninResponse =
        httpClient.post("/api/users/signin", request)
}
