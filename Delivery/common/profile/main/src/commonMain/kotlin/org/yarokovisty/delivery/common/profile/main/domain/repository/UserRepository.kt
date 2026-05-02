package org.yarokovisty.delivery.common.profile.main.domain.repository

import org.yarokovisty.delivery.common.profile.main.domain.entity.User

interface UserRepository {

    suspend fun getUserFromLocal(): User?

    suspend fun getUserFromNetwork(token: String): User

    suspend fun updateUser(user: User, token: String)
}
