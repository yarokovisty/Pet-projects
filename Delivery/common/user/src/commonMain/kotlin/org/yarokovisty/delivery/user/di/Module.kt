package org.yarokovisty.delivery.user.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.user.data.datasource.UserLocalDataSource
import org.yarokovisty.delivery.user.data.repository.UserRepositoryImpl
import org.yarokovisty.delivery.user.domain.repository.UserRepository
import org.yarokovisty.delivery.user.domain.usecase.IsUserAuthorizedUseCase
import org.yarokovisty.delivery.user.domain.usecase.RemoveUserPhoneNumberUseCase
import org.yarokovisty.delivery.user.domain.usecase.SaveUserPhoneNumberUseCase

val userModule = module {
    factoryOf(::UserLocalDataSource)
    factoryOf(::UserRepositoryImpl) bind UserRepository::class

    factoryOf(::SaveUserPhoneNumberUseCase)
    factoryOf(::RemoveUserPhoneNumberUseCase)
    factoryOf(::IsUserAuthorizedUseCase)
}
