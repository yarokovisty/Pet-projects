package org.yarokovisty.delivery.feature.delivery.main.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.yarokovisty.delivery.core.common.coroutines.DispatchersQualifier
import org.yarokovisty.delivery.feature.delivery.main.domain.usecase.GetAlternativeDeliveryPointsUseCase
import org.yarokovisty.delivery.feature.delivery.main.domain.usecase.GetDeliveryPointByNameUseCase
import org.yarokovisty.delivery.feature.delivery.main.presentation.viewmodel.DeliveryMainViewModel

val deliveryMainModule = module {
    factoryOf(::GetAlternativeDeliveryPointsUseCase)
    factory {
        GetDeliveryPointByNameUseCase(get(named(DispatchersQualifier.DEFAULT)))
    }

    viewModelOf(::DeliveryMainViewModel)
}
