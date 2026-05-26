package org.yarokovisty.delivery.common.profile.main.data.repository

import org.yarokovisty.delivery.common.profile.main.data.datasource.UserLocalDataSource
import org.yarokovisty.delivery.common.profile.main.data.datasource.UserRemoteDataSource
import org.yarokovisty.delivery.common.profile.main.data.mapper.toItem
import org.yarokovisty.delivery.common.profile.main.data.mapper.toRequest
import org.yarokovisty.delivery.common.profile.main.domain.entity.User
import org.yarokovisty.delivery.common.profile.main.domain.repository.UserRepository

internal class UserRepositoryImpl(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource,
) : UserRepository {

    override suspend fun getFromLocal(): User? =
        localDataSource.get()

    override suspend fun getFromNetwork(token: String): User =
        remoteDataSource.getUser(token).user.toItem()

    override suspend fun update(user: User, token: String) {
        remoteDataSource.updateUser(user.toRequest(), token)
        localDataSource.save(user)
    }

    override suspend fun clear() {
        localDataSource.remove()
    }
}
