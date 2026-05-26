package org.yarokovisty.delivery.common.profile.main.domain.repository

import org.yarokovisty.delivery.common.profile.main.domain.entity.User

interface UserRepository {

    suspend fun getFromLocal(): User?

    suspend fun getFromNetwork(token: String): User

    suspend fun update(user: User, token: String)

    suspend fun clear()
}
