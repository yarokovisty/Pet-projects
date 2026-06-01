package org.yarokovisty.delivery.common.profile.main.domain.repository

import org.yarokovisty.delivery.common.profile.main.domain.entity.User

interface UserRepository {

    suspend fun get(): User

    suspend fun set(user: User)

    suspend fun update(user: User)

    suspend fun clear()
}
