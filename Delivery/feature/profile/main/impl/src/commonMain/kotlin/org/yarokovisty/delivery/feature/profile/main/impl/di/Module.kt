package org.yarokovisty.delivery.feature.profile.main.impl.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.feature.profile.main.api.domain.repository.UserRepository
import org.yarokovisty.delivery.feature.profile.main.impl.data.datasource.UserRemoteDataSource
import org.yarokovisty.delivery.feature.profile.main.impl.data.repository.UserRepositoryImpl
import org.yarokovisty.delivery.feature.profile.main.impl.domain.usecase.GetUserUseCase
import org.yarokovisty.delivery.feature.profile.main.impl.domain.usecase.UpdateUserUseCase
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.router.ProfileRouter
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.viewmodel.ProfileViewModel

val profileMainModule = module {
    factoryOf(::UserRemoteDataSource)
    factoryOf(::UserRepositoryImpl) bind UserRepository::class

    factoryOf(::GetUserUseCase)
    factoryOf(::UpdateUserUseCase)

    factoryOf(::ProfileRouter)
    viewModelOf(::ProfileViewModel)
}
