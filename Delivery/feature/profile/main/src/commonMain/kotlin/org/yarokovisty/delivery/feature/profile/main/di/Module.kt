package org.yarokovisty.delivery.feature.profile.main.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.feature.profile.main.data.datasource.UserLocalDataSource
import org.yarokovisty.delivery.feature.profile.main.data.datasource.UserRemoteDataSource
import org.yarokovisty.delivery.feature.profile.main.data.json.createJsonSerialization
import org.yarokovisty.delivery.feature.profile.main.data.repository.UserRepositoryImpl
import org.yarokovisty.delivery.feature.profile.main.domain.repository.UserRepository
import org.yarokovisty.delivery.feature.profile.main.domain.usecase.GetUserUseCase
import org.yarokovisty.delivery.feature.profile.main.domain.usecase.UpdateUserUseCase
import org.yarokovisty.delivery.feature.profile.main.presentation.viewmodel.ProfileViewModel

val profileMainModule = module {
    factoryOf(::createJsonSerialization)
    factoryOf(::UserLocalDataSource)
    factoryOf(::UserRemoteDataSource)
    factoryOf(::UserRepositoryImpl) bind UserRepository::class

    factoryOf(::GetUserUseCase)
    factoryOf(::UpdateUserUseCase)

    viewModelOf(::ProfileViewModel)
}
