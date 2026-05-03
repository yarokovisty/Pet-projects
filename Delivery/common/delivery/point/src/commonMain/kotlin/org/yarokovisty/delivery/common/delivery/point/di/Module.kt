package org.yarokovisty.delivery.common.delivery.point.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.common.delivery.point.data.datasource.AddressLocalDataSource
import org.yarokovisty.delivery.common.delivery.point.data.repository.AddressRepositoryImpl
import org.yarokovisty.delivery.common.delivery.point.domain.repository.AddressRepository

val deliveryPointModule = module {
    factoryOf(::AddressLocalDataSource)
    factoryOf(::AddressRepositoryImpl) bind AddressRepository::class
}
