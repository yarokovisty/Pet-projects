package org.yarokovisty.delivery.di.module

import org.koin.dsl.module
import org.yarokovisty.delivery.core.network.di.networkModule

val coreModule = module {
    includes(networkModule)
}
