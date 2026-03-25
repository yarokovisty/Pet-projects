package org.yarokovisty.delivery.feature.direction.impl.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.feature.direction.api.domain.repository.DirectionRepository
import org.yarokovisty.delivery.feature.direction.impl.data.repository.DirectionRepositoryImpl
import org.yarokovisty.delivery.feature.direction.impl.data.service.DirectionService
import org.yarokovisty.delivery.feature.direction.impl.data.service.DirectionServiceImpl

val directionModule = module {
    factoryOf(::DirectionServiceImpl) bind DirectionService::class
    factoryOf(::DirectionRepositoryImpl) bind DirectionRepository::class
}
