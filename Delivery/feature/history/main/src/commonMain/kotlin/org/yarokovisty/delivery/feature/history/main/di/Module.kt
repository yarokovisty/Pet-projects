package org.yarokovisty.delivery.feature.history.main.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.yarokovisty.delivery.feature.history.main.presentation.viewmodel.HistoryMainViewModel

val historyMainModule = module {
    viewModelOf(::HistoryMainViewModel)
}
