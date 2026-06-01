package org.yarokovisty.delivery.common.auth.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.common.auth.data.repository.AuthRepositoryImpl
import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.common.auth.domain.usecase.IsUserAuthorizedUseCase

val authModule = module {
    factoryOf(::AuthRepositoryImpl) bind AuthRepository::class
    factoryOf(::IsUserAuthorizedUseCase)
}
