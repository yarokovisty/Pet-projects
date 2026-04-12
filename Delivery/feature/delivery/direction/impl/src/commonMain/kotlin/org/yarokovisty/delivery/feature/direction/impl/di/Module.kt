package org.yarokovisty.delivery.feature.direction.impl.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.yarokovisty.delivery.feature.direction.impl.presentation.router.DirectionRouter
import org.yarokovisty.delivery.feature.direction.impl.presentation.viewmodel.DirectionViewModel

val deliveryDirectionModule = module {
    factoryOf(::DirectionRouter)
    viewModelOf(::DirectionViewModel)
}
