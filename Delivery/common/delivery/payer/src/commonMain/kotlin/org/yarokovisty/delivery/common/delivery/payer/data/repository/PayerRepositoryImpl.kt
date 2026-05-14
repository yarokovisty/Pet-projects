package org.yarokovisty.delivery.common.delivery.payer.data.repository

import org.yarokovisty.delivery.common.delivery.payer.data.datasource.PayerLocalDataSource
import org.yarokovisty.delivery.common.delivery.payer.domain.entity.Payer
import org.yarokovisty.delivery.common.delivery.payer.domain.repository.PayerRepository

internal class PayerRepositoryImpl(
    private val localDataSource: PayerLocalDataSource,
) : PayerRepository {

    override suspend fun getPayer(): Payer? =
        localDataSource.getPayer()

    override suspend fun setPayer(payer: Payer) {
        localDataSource.setPayer(payer)
    }
}
