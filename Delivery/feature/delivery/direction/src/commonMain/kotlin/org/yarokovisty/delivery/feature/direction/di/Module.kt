package org.yarokovisty.delivery.feature.direction.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.yarokovisty.delivery.feature.direction.presentation.viewmodel.DirectionViewModel

val deliveryDirectionModule = module {
    viewModelOf(::DirectionViewModel)
}
