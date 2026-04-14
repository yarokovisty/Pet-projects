package org.yarokovisty.common.delivery.calculator.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.common.delivery.calculator.data.datasource.CalculatorLocalDataSource
import org.yarokovisty.common.delivery.calculator.data.datasource.CalculatorRemoteDataSource
import org.yarokovisty.common.delivery.calculator.data.repository.CalculatorRepositoryImpl
import org.yarokovisty.common.delivery.calculator.domain.repository.CalculatorRepository

val deliveryCalculatorModule = module {
    singleOf(::CalculatorLocalDataSource)
    factoryOf(::CalculatorRemoteDataSource)
    factoryOf(::CalculatorRepositoryImpl) bind CalculatorRepository::class
}
