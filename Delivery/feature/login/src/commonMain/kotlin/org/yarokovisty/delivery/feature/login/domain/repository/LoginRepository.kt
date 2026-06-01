package org.yarokovisty.delivery.feature.login.domain.repository

import org.yarokovisty.delivery.common.profile.main.domain.entity.User

interface LoginRepository {

    suspend fun requestOtp(phoneNumber: String): Long

    suspend fun signin(phoneNumber: String, otpCode: Int): Pair<String, User>
}
