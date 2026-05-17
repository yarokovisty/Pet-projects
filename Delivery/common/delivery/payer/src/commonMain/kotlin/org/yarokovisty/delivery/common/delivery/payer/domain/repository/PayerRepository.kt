package org.yarokovisty.delivery.common.delivery.payer.domain.repository

import org.yarokovisty.delivery.common.delivery.payer.domain.entity.Payer

interface PayerRepository {

    suspend fun getPayer(): Payer?

    suspend fun setPayer(payer: Payer)

    suspend fun clearPayer()
}
