package org.yarokovisty.delivery.feature.login.impl.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.feature.login.api.domain.repository.LoginRepository
import org.yarokovisty.delivery.feature.login.impl.data.datasource.LoginRemoteDataSource
import org.yarokovisty.delivery.feature.login.impl.data.repository.LoginRepositoryImpl
import org.yarokovisty.delivery.feature.login.impl.domain.usecase.RuPhoneValidationUseCase
import org.yarokovisty.delivery.feature.login.impl.domain.usecase.SigninUseCase
import org.yarokovisty.delivery.feature.login.impl.domain.usecase.StartCountDownUseCase
import org.yarokovisty.delivery.feature.login.impl.domain.validator.OtpCodeFormatValidator
import org.yarokovisty.delivery.feature.login.impl.presentation.router.LoginRouter
import org.yarokovisty.delivery.feature.login.impl.presentation.viewmodel.LoginViewModel

val loginModule = module {
    factoryOf(::LoginRemoteDataSource)
    factoryOf(::LoginRepositoryImpl) bind LoginRepository::class

    factoryOf(::RuPhoneValidationUseCase)
    factoryOf(::SigninUseCase)
    factoryOf(::StartCountDownUseCase)
    factoryOf(::OtpCodeFormatValidator)

    factoryOf(::LoginRouter)
    viewModelOf(::LoginViewModel)
}
