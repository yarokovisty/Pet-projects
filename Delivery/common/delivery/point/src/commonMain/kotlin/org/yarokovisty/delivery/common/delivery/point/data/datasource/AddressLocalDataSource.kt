package org.yarokovisty.delivery.common.delivery.point.data.datasource

import org.yarokovisty.delivery.common.delivery.point.domain.entity.Address
import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage

internal class AddressLocalDataSource(private val storage: PreferencesStorage) {

    private companion object {

        const val SENDER_ADDRESS_KEY = "senderAddress"
        const val RECEIVER_ADDRESS_KEY = "receiverAddress"
    }

    suspend fun getSender(): Address? =
        storage.getObject(SENDER_ADDRESS_KEY, Address.serializer())

    suspend fun setSender(address: Address) {
        storage.putObject(SENDER_ADDRESS_KEY, address, Address.serializer())
    }

    suspend fun clearSender() {
        storage.remove(SENDER_ADDRESS_KEY)
    }

    suspend fun getReceiver(): Address? =
        storage.getObject(RECEIVER_ADDRESS_KEY, Address.serializer())

    suspend fun setReceiver(address: Address) {
        storage.putObject(RECEIVER_ADDRESS_KEY, address, Address.serializer())
    }

    suspend fun clearReceiver() {
        storage.remove(RECEIVER_ADDRESS_KEY)
    }
}
