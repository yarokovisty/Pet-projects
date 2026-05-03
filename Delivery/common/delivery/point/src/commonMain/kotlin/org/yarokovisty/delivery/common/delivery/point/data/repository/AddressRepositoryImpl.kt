package org.yarokovisty.delivery.common.delivery.point.data.repository

import org.yarokovisty.delivery.common.delivery.point.data.datasource.AddressLocalDataSource
import org.yarokovisty.delivery.common.delivery.point.domain.entity.Address
import org.yarokovisty.delivery.common.delivery.point.domain.repository.AddressRepository

internal class AddressRepositoryImpl(
    private val localDataSource: AddressLocalDataSource,
) : AddressRepository {

    override suspend fun getSender(): Address? =
        localDataSource.getSender()

    override suspend fun setSender(address: Address) {
        localDataSource.setSender(address)
    }

    override suspend fun getReceiver(): Address? =
        localDataSource.getReceiver()

    override suspend fun setReceiver(address: Address) {
        localDataSource.setReceiver(address)
    }
}
