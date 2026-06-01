package org.yarokovisty.delivery.common.profile.main.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.common.profile.main.data.datasource.UserLocalDataSource
import org.yarokovisty.delivery.common.profile.main.data.datasource.UserRemoteDataSource
import org.yarokovisty.delivery.common.profile.main.data.repository.UserRepositoryImpl
import org.yarokovisty.delivery.common.profile.main.domain.repository.UserRepository
import org.yarokovisty.delivery.core.network.di.authHttpClientQualifier

val profileMainModule = module {
    factoryOf(::UserLocalDataSource)
    factory { UserRemoteDataSource(authHttpClient = get(authHttpClientQualifier)) }
    factoryOf(::UserRepositoryImpl) bind UserRepository::class
}
