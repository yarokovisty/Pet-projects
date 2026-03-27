package org.yarokovisty.delivery.user.data.datasource

import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage

internal class UserLocalDataSource(private val storage: PreferencesStorage) {

    private companion object {

        const val USER_PHONE_NUMBER_KEY = "user_phone_number"
    }

    suspend fun savePhoneNumber(phoneNumber: String) {
        storage.putString(USER_PHONE_NUMBER_KEY, phoneNumber)
    }

    suspend fun fetchPhoneNumber(): String? =
        storage.getString(USER_PHONE_NUMBER_KEY)

    suspend fun clearPhoneNumber() {
        storage.remove(USER_PHONE_NUMBER_KEY)
    }
}
