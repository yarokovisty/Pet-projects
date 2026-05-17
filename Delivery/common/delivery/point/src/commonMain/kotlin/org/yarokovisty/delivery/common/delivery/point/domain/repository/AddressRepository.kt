package org.yarokovisty.delivery.common.delivery.point.domain.repository

import org.yarokovisty.delivery.common.delivery.point.domain.entity.Address

interface AddressRepository {

    suspend fun getSender(): Address?

    suspend fun setSender(address: Address)

    suspend fun clearSender()

    suspend fun getReceiver(): Address?

    suspend fun setReceiver(address: Address)

    suspend fun clearReceiver()
}
