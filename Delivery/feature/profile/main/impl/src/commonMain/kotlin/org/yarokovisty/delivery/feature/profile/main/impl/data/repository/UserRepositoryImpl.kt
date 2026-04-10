package org.yarokovisty.delivery.feature.profile.main.impl.data.repository

import org.yarokovisty.delivery.feature.profile.main.api.domain.entity.User
import org.yarokovisty.delivery.feature.profile.main.api.domain.repository.UserRepository
import org.yarokovisty.delivery.feature.profile.main.impl.data.datasource.UserRemoteDataSource
import org.yarokovisty.delivery.feature.profile.main.impl.data.mapper.toItem
import org.yarokovisty.delivery.feature.profile.main.impl.data.mapper.toRequest

internal class UserRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
) : UserRepository {

    override suspend fun getUser(token: String): User =
        remoteDataSource.getUser(token).user.toItem()

    override suspend fun updateUser(user: User, token: String) {
        remoteDataSource.updateUser(user.toRequest(), token)
    }
}
