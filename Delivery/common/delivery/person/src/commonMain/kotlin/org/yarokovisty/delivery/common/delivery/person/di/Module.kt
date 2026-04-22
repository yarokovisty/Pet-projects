package org.yarokovisty.delivery.common.delivery.person.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.common.delivery.person.data.datasource.PersonLocalDataSource
import org.yarokovisty.delivery.common.delivery.person.data.repository.PersonRepositoryImpl
import org.yarokovisty.delivery.common.delivery.person.domain.repository.PersonRepository

val deliveryPersonModule = module {
    factoryOf(::PersonLocalDataSource)
    factoryOf(::PersonRepositoryImpl) bind PersonRepository::class
}
