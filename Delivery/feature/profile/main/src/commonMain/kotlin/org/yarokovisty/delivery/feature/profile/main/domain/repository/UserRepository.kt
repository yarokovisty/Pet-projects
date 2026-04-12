package org.yarokovisty.delivery.feature.profile.main.domain.repository

import org.yarokovisty.delivery.feature.profile.main.domain.entity.User

interface UserRepository {

    suspend fun getUser(token: String): User

    suspend fun updateUser(user: User, token: String)
}
