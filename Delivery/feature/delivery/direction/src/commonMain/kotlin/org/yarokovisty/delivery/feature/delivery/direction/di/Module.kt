package org.yarokovisty.delivery.feature.delivery.direction.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.yarokovisty.delivery.feature.delivery.direction.presentation.viewmodel.DirectionViewModel

val deliveryDirectionModule = module {
    viewModelOf(::DirectionViewModel)
}
