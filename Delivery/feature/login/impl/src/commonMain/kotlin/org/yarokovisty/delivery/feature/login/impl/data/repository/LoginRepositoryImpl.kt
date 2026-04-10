package org.yarokovisty.delivery.feature.login.impl.data.repository

import org.yarokovisty.delivery.feature.login.api.domain.repository.LoginRepository
import org.yarokovisty.delivery.feature.login.api.error.LoginError
import org.yarokovisty.delivery.feature.login.impl.data.datasource.LoginRemoteDataSource
import org.yarokovisty.delivery.feature.login.impl.data.model.OtpRequest
import org.yarokovisty.delivery.feature.login.impl.data.model.SigninRequest

internal class LoginRepositoryImpl(
    private val remoteDataSource: LoginRemoteDataSource
) : LoginRepository {

    private companion object {
        const val INVALID_OTP_REASON = "Неправильный отп код"
    }

    override suspend fun requestOtp(phoneNumber: String): Long {
        val request = OtpRequest(phoneNumber)
        val response = remoteDataSource.otp(request)

        return response.retryDelay
    }

    override suspend fun signin(phoneNumber: String, otpCode: Int): String {
        val request = SigninRequest(phoneNumber, otpCode)
        val response = remoteDataSource.signin(request)

        return when {
            response.success && response.token != null -> response.token
            response.reason == INVALID_OTP_REASON -> throw LoginError.InvalidOtp
            else -> throw LoginError.Unknown
        }
    }
}
