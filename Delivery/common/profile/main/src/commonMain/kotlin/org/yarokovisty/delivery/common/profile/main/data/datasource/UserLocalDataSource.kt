package org.yarokovisty.delivery.common.profile.main.data.datasource

import org.yarokovisty.delivery.common.profile.main.domain.entity.User
import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage

internal class UserLocalDataSource(private val storage: PreferencesStorage) {

    private companion object {

        const val USER_KEY = "user"
    }

    suspend fun get(): User? =
        storage.getObject(USER_KEY, User.serializer())

    suspend fun save(user: User) {
        storage.putObject(USER_KEY, user, User.serializer())
    }
}
