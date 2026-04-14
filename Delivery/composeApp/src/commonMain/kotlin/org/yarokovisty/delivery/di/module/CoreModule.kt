package org.yarokovisty.delivery.di.module

import org.koin.dsl.module
import org.yarokovisty.delivery.core.common.coroutines.coroutinesModule
import org.yarokovisty.delivery.core.network.di.networkModule
import org.yarokovisty.delivery.core.storage.di.storageModule

val coreModule = module {
    includes(
        coroutinesModule,
        networkModule,
        serializationModule,
        storageModule,
    )
}
