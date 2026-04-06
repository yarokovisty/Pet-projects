package org.yarokovisty.delivery.core.storage.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import org.yarokovisty.delivery.core.storage.datastore.createDataStore

internal actual val dataStoreModule: Module = module {
    single { createDataStore(androidContext()) }
}
