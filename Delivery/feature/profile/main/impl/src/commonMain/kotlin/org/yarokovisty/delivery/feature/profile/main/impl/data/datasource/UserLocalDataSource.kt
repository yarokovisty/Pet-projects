package org.yarokovisty.delivery.feature.profile.main.impl.data.datasource

import io.ktor.utils.io.CancellationException
import kotlinx.serialization.json.Json
import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage
import org.yarokovisty.delivery.feature.profile.main.api.domain.entity.User

internal class UserLocalDataSource(
    private val json: Json,
    private val storage: PreferencesStorage
) {

    private companion object {

        const val USER_KEY = "user"
    }

    suspend fun get(): User? =
        try {
            storage.getString(USER_KEY)?.let { jsonString ->
                json.decodeFromString<User>(jsonString)
            }
        } catch (ex: CancellationException) {
            throw ex
        } catch (_: Exception) {
            null
        }

    suspend fun save(user: User) {
        try {
            val jsonString = json.encodeToString(user)
            storage.putString(USER_KEY, jsonString)
        } catch (ex: CancellationException) {
            throw ex
        } catch (_: Exception) {
        }
    }
}
