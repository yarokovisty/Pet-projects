package org.yarokovisty.delivery.common.delivery.direction.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.common.delivery.direction.data.datasource.DirectionLocalDataSource
import org.yarokovisty.delivery.common.delivery.direction.data.datasource.DirectionRemoteDataSource
import org.yarokovisty.delivery.common.delivery.direction.data.repository.DirectionRepositoryImpl
import org.yarokovisty.delivery.common.delivery.direction.domain.repository.DirectionRepository
import org.yarokovisty.delivery.core.network.di.defaultHttpClientQualifier

val deliveryDirectionModule = module {
    factoryOf(::DirectionLocalDataSource)
    factory {
        DirectionRemoteDataSource(httpClient = get(defaultHttpClientQualifier))
    }
    factoryOf(::DirectionRepositoryImpl) bind DirectionRepository::class
}
