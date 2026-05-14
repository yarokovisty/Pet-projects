package org.yarokovisty.delivery.common.delivery.payer.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.common.delivery.payer.data.datasource.PayerLocalDataSource
import org.yarokovisty.delivery.common.delivery.payer.data.repository.PayerRepositoryImpl
import org.yarokovisty.delivery.common.delivery.payer.domain.repository.PayerRepository

val deliveryPayerModule = module {
    factoryOf(::PayerLocalDataSource)
    factoryOf(::PayerRepositoryImpl) bind PayerRepository::class
}
