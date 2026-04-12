package org.yarokovisty.common.delivery.direction.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.common.delivery.direction.data.datasource.DirectionRemoteDataSource
import org.yarokovisty.common.delivery.direction.data.repository.DirectionRepositoryImpl
import org.yarokovisty.common.delivery.direction.domain.repository.DirectionRepository

val deliveryDirectionModule = module {
    factoryOf(::DirectionRemoteDataSource)
    factoryOf(::DirectionRepositoryImpl) bind DirectionRepository::class
}
