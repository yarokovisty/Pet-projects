package org.yarokovisty.delivery.common.delivery.person.data.datasource

import kotlinx.serialization.builtins.serializer
import org.yarokovisty.delivery.common.delivery.person.domain.entity.PersonInfo
import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage

internal class PersonLocalDataSource(private val storage: PreferencesStorage) {

    private companion object {

        const val RECEIVER_KEY = "receiver_person"
        const val SENDER_KEY = "sender_person"
    }

    suspend fun getReceiver(): PersonInfo? =
        storage.getObject(RECEIVER_KEY, PersonInfo.serializer())

    suspend fun setReceiver(receiver: PersonInfo) {
        storage.putObject(RECEIVER_KEY, receiver, PersonInfo.serializer())
    }

    suspend fun getSender(): PersonInfo? =
        storage.getObject(SENDER_KEY, PersonInfo.serializer())

    suspend fun setSender(sender: PersonInfo) {
        storage.putObject(SENDER_KEY, sender, PersonInfo.serializer())
    }
}
