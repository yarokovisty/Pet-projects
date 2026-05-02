package org.yarokovisty.delivery.common.profile.main.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.common.profile.main.data.datasource.UserLocalDataSource
import org.yarokovisty.delivery.common.profile.main.data.datasource.UserRemoteDataSource
import org.yarokovisty.delivery.common.profile.main.data.repository.UserRepositoryImpl
import org.yarokovisty.delivery.common.profile.main.domain.repository.UserRepository
import org.yarokovisty.delivery.common.profile.main.domain.usecase.GetUserUseCase
import org.yarokovisty.delivery.common.profile.main.domain.usecase.UpdateUserUseCase

val profileMainModule = module {
    factoryOf(::UserLocalDataSource)
    factoryOf(::UserRemoteDataSource)
    factoryOf(::UserRepositoryImpl) bind UserRepository::class

    factoryOf(::GetUserUseCase)
    factoryOf(::UpdateUserUseCase)
}
