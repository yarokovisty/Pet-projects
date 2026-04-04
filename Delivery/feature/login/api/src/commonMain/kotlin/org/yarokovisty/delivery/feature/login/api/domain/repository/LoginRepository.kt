package org.yarokovisty.delivery.feature.login.api.domain.repository

interface LoginRepository {

    suspend fun requestOtp(phoneNumber: String): Long

    suspend fun signin(phoneNumber: String, otpCode: Int): String
}
