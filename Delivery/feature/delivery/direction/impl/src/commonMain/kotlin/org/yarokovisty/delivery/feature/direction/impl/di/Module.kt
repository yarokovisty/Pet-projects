package org.yarokovisty.delivery.feature.direction.impl.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.feature.direction.api.domain.repository.DirectionRepository
import org.yarokovisty.delivery.feature.direction.impl.data.datasource.DirectionRemoteDataSource
import org.yarokovisty.delivery.feature.direction.impl.data.repository.DirectionRepositoryImpl
import org.yarokovisty.delivery.feature.direction.impl.presentation.router.DirectionRouter
import org.yarokovisty.delivery.feature.direction.impl.presentation.viewmodel.DirectionViewModel

val directionModule = module {
    factoryOf(::DirectionRemoteDataSource)
    factoryOf(::DirectionRepositoryImpl) bind DirectionRepository::class

    factoryOf(::DirectionRouter)
    viewModelOf(::DirectionViewModel)
}
