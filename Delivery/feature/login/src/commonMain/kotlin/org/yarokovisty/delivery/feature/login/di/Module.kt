package org.yarokovisty.delivery.feature.login.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.core.network.di.defaultHttpClientQualifier
import org.yarokovisty.delivery.feature.login.data.datasource.LoginRemoteDataSource
import org.yarokovisty.delivery.feature.login.data.repository.LoginRepositoryImpl
import org.yarokovisty.delivery.feature.login.domain.repository.LoginRepository
import org.yarokovisty.delivery.feature.login.domain.usecase.SigninUseCase
import org.yarokovisty.delivery.feature.login.domain.usecase.StartCountDownUseCase
import org.yarokovisty.delivery.feature.login.domain.validator.OtpCodeFormatValidator
import org.yarokovisty.delivery.feature.login.presentation.viewmodel.LoginViewModel

val loginModule = module {
    factory { LoginRemoteDataSource(httpClient = get(defaultHttpClientQualifier)) }
    factoryOf(::LoginRepositoryImpl) bind LoginRepository::class

    factoryOf(::SigninUseCase)
    factoryOf(::StartCountDownUseCase)
    factoryOf(::OtpCodeFormatValidator)

    viewModelOf(::LoginViewModel)
}
