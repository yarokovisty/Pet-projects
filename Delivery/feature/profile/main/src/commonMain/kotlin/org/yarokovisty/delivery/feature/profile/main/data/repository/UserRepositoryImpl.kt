package org.yarokovisty.delivery.feature.profile.main.data.repository

import org.yarokovisty.delivery.feature.profile.main.data.mapper.toItem
import org.yarokovisty.delivery.feature.profile.main.data.mapper.toRequest
import org.yarokovisty.delivery.feature.profile.main.domain.entity.User
import org.yarokovisty.delivery.feature.profile.main.domain.repository.UserRepository

internal class UserRepositoryImpl(
    private val localDataSource: org.yarokovisty.delivery.feature.profile.main.data.datasource.UserLocalDataSource,
    private val remoteDataSource: org.yarokovisty.delivery.feature.profile.main.data.datasource.UserRemoteDataSource,
) : UserRepository {

    override suspend fun getUser(token: String): User =
        localDataSource.get()
            ?: remoteDataSource.getUser(token).user.toItem().also { localDataSource.save(it) }

    override suspend fun updateUser(user: User, token: String) {
        remoteDataSource.updateUser(user.toRequest(), token)
        localDataSource.save(user)
    }
}
