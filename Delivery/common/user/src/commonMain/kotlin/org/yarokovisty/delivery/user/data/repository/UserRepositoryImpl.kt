package org.yarokovisty.delivery.user.data.repository

import org.yarokovisty.delivery.user.data.datasource.UserLocalDataSource
import org.yarokovisty.delivery.user.domain.repository.UserRepository

internal class UserRepositoryImpl(
    private val localDataSource: UserLocalDataSource
) : UserRepository {

    override suspend fun saveUserPhoneNumber(phoneNumber: String) {
        localDataSource.savePhoneNumber(phoneNumber)
    }

    override suspend fun removeUserPhoneNumber() {
        localDataSource.clearPhoneNumber()
    }

    override suspend fun getAuthPhoneNumber(): String? =
        localDataSource.fetchPhoneNumber()
}
