package org.yarokovisty.delivery.feature.history.details.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.yarokovisty.delivery.feature.history.details.presentation.viewmodel.OrderDetailsViewModel

val historyDetailsModule = module {
    viewModel { (orderId: String) ->
        OrderDetailsViewModel(
            orderRepository = get(),
            router = get(),
            orderId = orderId
        )
    }
}
