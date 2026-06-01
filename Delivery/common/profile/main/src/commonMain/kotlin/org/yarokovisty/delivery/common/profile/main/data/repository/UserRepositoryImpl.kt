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

    override suspend fun get(): User =
        localDataSource.get()
            ?: remoteDataSource.get().user.toItem().also { localDataSource.save(it) }

    override suspend fun set(user: User) =
        localDataSource.save(user)

    override suspend fun update(user: User) {
        remoteDataSource.update(user.toRequest())
        localDataSource.save(user)
    }

    override suspend fun clear() {
        localDataSource.remove()
    }
}
