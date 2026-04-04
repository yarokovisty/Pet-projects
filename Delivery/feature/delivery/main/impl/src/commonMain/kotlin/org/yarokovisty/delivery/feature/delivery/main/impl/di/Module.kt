package org.yarokovisty.delivery.feature.delivery.main.impl.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.common.coroutines.DispatchersQualifier
import org.yarokovisty.delivery.feature.delivery.main.api.domain.repository.DeliveryRepository
import org.yarokovisty.delivery.feature.delivery.main.impl.data.datasource.DeliveryRemoteDataSource
import org.yarokovisty.delivery.feature.delivery.main.impl.data.repository.DeliveryRepositoryImpl
import org.yarokovisty.delivery.feature.delivery.main.impl.domain.usecase.GetAlternativeDeliveryPointsUseCase
import org.yarokovisty.delivery.feature.delivery.main.impl.domain.usecase.GetDeliveryPointByNameUseCase
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.router.DeliveryRouter
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.viewmodel.DeliveryMainViewModel

val deliveryMainModule = module {
    factoryOf(::DeliveryRemoteDataSource)
    factoryOf(::DeliveryRepositoryImpl) bind DeliveryRepository::class

    factoryOf(::GetAlternativeDeliveryPointsUseCase)
    factory {
        GetDeliveryPointByNameUseCase(
            get(named(DispatchersQualifier.DEFAULT))
        )
    }

    factoryOf(::DeliveryRouter)
    viewModelOf(::DeliveryMainViewModel)
}
