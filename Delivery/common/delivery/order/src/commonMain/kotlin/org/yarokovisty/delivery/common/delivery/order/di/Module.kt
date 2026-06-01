package org.yarokovisty.delivery.common.delivery.order.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.common.delivery.order.data.datasource.OrderRemoteDataSource
import org.yarokovisty.delivery.common.delivery.order.data.repository.OrderRepositoryImpl
import org.yarokovisty.delivery.common.delivery.order.domain.repository.OrderRepository
import org.yarokovisty.delivery.common.delivery.order.domain.usecase.ClearConfirmationOrderUseCase
import org.yarokovisty.delivery.common.delivery.order.domain.usecase.GetConfirmationOrderUseCase
import org.yarokovisty.delivery.core.network.di.authHttpClientQualifier
import org.yarokovisty.delivery.core.network.di.defaultHttpClientQualifier

val deliveryOrderModule = module {
    factory {
        OrderRemoteDataSource(
            defaultHttpClient = get(defaultHttpClientQualifier),
            authHttpClient = get(authHttpClientQualifier)
        )
    }
    factoryOf(::OrderRepositoryImpl) bind OrderRepository::class
    factoryOf(::ClearConfirmationOrderUseCase)
    factoryOf(::GetConfirmationOrderUseCase)
}
