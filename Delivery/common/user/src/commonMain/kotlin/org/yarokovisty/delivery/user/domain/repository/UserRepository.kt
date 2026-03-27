package org.yarokovisty.delivery.user.domain.repository

interface UserRepository {

    suspend fun saveUserPhoneNumber(phoneNumber: String)

    suspend fun removeUserPhoneNumber()

    suspend fun getAuthPhoneNumber(): String?
}
